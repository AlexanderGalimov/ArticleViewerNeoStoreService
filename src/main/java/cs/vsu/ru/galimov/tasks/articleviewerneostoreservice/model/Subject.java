package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Node("Subject")
public class Subject {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private List<String> authorsNames;

    private String departmentMagazineName;

    @Relationship(type = "RELATED_TO")
    private Set<Subject> relatedSubjects;

    public Subject(String title, List<String> authorsNames, String departmentMagazineName) {
        this.title = title;
        this.authorsNames = authorsNames;
        this.departmentMagazineName = departmentMagazineName;
    }

    public Subject() {
    }

    public void addRelatedSubject(Subject subject) {
        if (relatedSubjects == null) {
            relatedSubjects = new HashSet<>();
        }
        relatedSubjects.add(subject);
    }
}
