package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.ArticleServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.AuthorServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RelationshipCreator {

    private final SubjectServiceImpl subjectService;

    private final ArticleServiceImpl articleService;

    private final AuthorServiceImpl authorService;

    private final ReferencesSeparator separator;

    @Autowired
    public RelationshipCreator(SubjectServiceImpl subjectService, ArticleServiceImpl articleService, AuthorServiceImpl authorService, ReferencesSeparator separator) {
        this.subjectService = subjectService;
        this.articleService = articleService;
        this.authorService = authorService;
        this.separator = separator;
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

//    public void createRelationship(String departmentMagazineName) {
//        List<Subject> allSubjects = subjectService.findByDepartmentMagazineName(departmentMagazineName);
//        for (Subject currentSubject : allSubjects) {
//
//            Article currentArticle = articleService.findByPdfParamsTitle(currentSubject.getTitle());
//
//            String fullText = currentArticle.getFullText();
//            if (!Objects.equals(fullText, "")) {
//                Set<String> relatedAuthors = separator.getRelatedAuthors(fullText);
//                for (String relatedAuthorName : relatedAuthors) {
//                    String lastName = extractLastName(relatedAuthorName);
//                    List<Author> authors = authorService.findByNameContains(lastName);
//                    List<Subject> subjects = new ArrayList<>();
//                    Author resultAuthor = null;
//                    for (Author author : authors) {
//                        String authorName = author.getName();
//                        String shortName = shortFullName(authorName);
//                        if (areEquivalentNames(shortName, relatedAuthorName)) {
//                            resultAuthor = author;
//                            break;
//                        }
//                    }
//                    if (resultAuthor != null) {
//                        subjects = subjectService.findByAuthorsNamesContaining(resultAuthor.getName());
//                    }
//                    for (Subject relatedSubject : subjects) {
//                        if(!(Objects.equals(relatedSubject.getTitle(), currentSubject.getTitle()))){
//                            currentSubject.addRelatedSubject(relatedSubject);
//                            subjectService.updateSubject(currentSubject.getId(), currentSubject);
//                        }
//                    }
//                }
//            }
//        }
//    }
}
