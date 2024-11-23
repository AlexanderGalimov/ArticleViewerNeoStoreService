package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.relationship;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.activity.ActivityMonitor;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.relationship.model.ParsedValuePair;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.SubjectStatus;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.ArticleServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.AuthorServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.SubjectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RelationshipCreator {

    private final SubjectServiceImpl subjectService;

    private final ArticleServiceImpl articleService;

    private final AuthorServiceImpl authorService;

    private final ReferencesSeparator separator;

    private final Logger logger = LoggerFactory.getLogger(RelationshipCreator.class);

    private final ActivityMonitor monitor;

    @Autowired
    public RelationshipCreator(SubjectServiceImpl subjectService, ArticleServiceImpl articleService, AuthorServiceImpl authorService, ReferencesSeparator separator, ActivityMonitor monitor) {
        this.subjectService = subjectService;
        this.articleService = articleService;
        this.authorService = authorService;
        this.separator = separator;
        this.monitor = monitor;
    }

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

        monitor.updateLastActivity();

        Subject subject;
        List<String> authorsNames = getAuthorsNames(article);
        try {
            if (subjectService.findByTitleAndAuthorsNames(article.getPdfParams().getTitle(), authorsNames) == null) {
                subject = subjectService.createSubject(new Subject(article.getPdfParams().getTitle(), SubjectStatus.FOUND, authorsNames, article.getDepartmentMagazine().getName()));
            } else {
                subject = subjectService.findByTitleAndAuthorsNames(article.getPdfParams().getTitle(), authorsNames);
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
                        Subject referencedArticleSubject = subjectService.findByTitleAndAuthorsNames(referencedArticle.getPdfParams().getTitle(), referencedArticleAuthorsNames);
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
                logger.info("References is null for article: " + article.getUniqUIIDS3());
            }
        } catch (Exception e) {
            logger.error("Got exception for:" + article.getPdfParams().getTitle());
            logger.error(e.getMessage());
            System.exit(-1);
        }
    }

    public String extractLastName(String fullName) {
        String[] parts = fullName.split(" ");

        if (parts.length != 3) {
            parts = fullName.split("\\.");
        }
        String lastName = parts[parts.length - 1];
        for (String part : parts) {
            if (part.length() > lastName.length()) {
                lastName = part;
            }
        }
        lastName = lastName.replaceAll("\\.", "");
        return lastName;
    }

    public String shortFullName(String fullName) {
        String[] parts = fullName.split(" ");
        StringBuilder shortName = new StringBuilder();
        if (parts.length == 3) {
            char firstNameInitial = parts[0].charAt(0);
            char middleNameInitial = parts[1].charAt(0);
            shortName.append(firstNameInitial).append(".").append(" ").append(middleNameInitial).append(".").append(" ").append(parts[2]);
        } else {
            shortName.append(fullName);
        }
        return shortName.toString();
    }

    public boolean areEquivalentNames(String fullName1, String fullName2) {
        fullName1 = fullName1.replace(".", "");
        fullName2 = fullName2.replace(".", "");
        String[] parts1 = fullName1.split(" ");
        String[] parts2 = fullName2.split(" ");

        if (parts1.length != 3 || parts2.length != 3) {
            return false;
        }

        if (parts1[2].equals(parts2[0])) {
            return parts1[0].equals(parts2[1]) && parts1[1].equals(parts2[2]);
        } else if (parts1[0].equals(parts2[0]) && parts1[1].equals(parts2[1])) {
            return parts1[2].equals(parts2[2]);
        }
        return false;
    }
}
