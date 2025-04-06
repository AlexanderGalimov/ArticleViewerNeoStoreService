package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.relationship;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.SubjectStatus;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.relationship.model.ParsedValuePair;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.ArticleServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.AuthorServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RelationshipCreator {

    private final SubjectServiceImpl subjectService;

    private final ArticleServiceImpl articleService;

    private final AuthorServiceImpl authorService;

    private final ReferencesSeparator separator;

    public List<String> getAuthorsNames(Article article) {
        List<String> authorsNames = new ArrayList<>();
        for (String authorsIds : article.getAuthorIds()) {
            Author author = authorService.findById(authorsIds);
            authorsNames.add(author.getName());
        }
        return authorsNames;
    }

    public void createRelationShip(Article article) {
        List<ParsedValuePair> references = separator.getReferences(article);

        Subject subject;
        List<String> authorsNames = getAuthorsNames(article);
        try {
            subject = subjectService.findByTitleAndAnyAuthorInAuthorsNames(article.getPdfParams().getTitle(), authorsNames);
            if (subject == null) {
                subject = subjectService.createSubject(new Subject(article.getPdfParams().getTitle(), SubjectStatus.FOUND, authorsNames, article.getDepartmentMagazine().getName()));
            } else {
                if (subject.getStatus() == SubjectStatus.CREATED) {
                    subject.setStatus(SubjectStatus.FOUND);
                    subject.setDepartmentMagazineName(article.getDepartmentMagazine().getName());
                    subjectService.updateSubject(subject.getId(), subject);
                }
            }
            if (references != null) {
                for (ParsedValuePair pair : references) {
                    Article referencedArticle = articleService.findByPdfParamsTitle(pair.title());
                    if (referencedArticle != null) {
                        List<String> referencedArticleAuthorsNames = getAuthorsNames(referencedArticle);
                        //
                        Subject referencedArticleSubject = subjectService.findByTitleAndAnyAuthorInAuthorsNames(referencedArticle.getPdfParams().getTitle(), referencedArticleAuthorsNames);
                        if (referencedArticleSubject == null) {
                            Subject newReferencedArticleSubject = subjectService.createSubject(new Subject(referencedArticle.getPdfParams().getTitle(), SubjectStatus.FOUND, referencedArticleAuthorsNames, referencedArticle.getDepartmentMagazine().getName()));
                            subject.addRelatedSubject(newReferencedArticleSubject);
                        } else {
                            subject.addRelatedSubject(referencedArticleSubject);
                        }
                    } else {
                        Subject oldUnchekedSubject = subjectService.findByTitleAndAuthorsNamesContaining(pair.title(), pair.author());
                        if (oldUnchekedSubject == null) {
                            List<String> authors = new ArrayList<>();
                            authors.add(pair.author());
                            Subject uncheckedSubject = subjectService.createSubject(new Subject(pair.title(), SubjectStatus.CREATED, authors, null));
                            subject.addRelatedSubject(uncheckedSubject);
                        } else {
                            subject.addRelatedSubject(oldUnchekedSubject);
                        }
                    }
                    subject.setStatus(SubjectStatus.RELATED);
                    subjectService.updateSubject(subject.getId(), subject);
                }
            } else {
                log.info("References is null for article: " + article.getUniqUIIDS3());
            }
        } catch (Exception e) {
            log.error("Got exception for: {}", article.getPdfParams().getTitle());
            log.error(e.getMessage());
        }
    }
}
