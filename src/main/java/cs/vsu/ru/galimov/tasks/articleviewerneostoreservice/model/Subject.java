package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Node("Subject")
public class Subject {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private SubjectStatus status;

    private List<String> authorsNames;

    private String departmentMagazineName;

    @Relationship(type = "RELATED_TO")
    private Set<Subject> relatedSubjects;

    public Subject(String title, SubjectStatus status, List<String> authorsNames, String departmentMagazineName) {
        this.title = title;
        this.status = status;
        this.authorsNames = authorsNames;
        this.departmentMagazineName = departmentMagazineName;
    }

    public void addRelatedSubject(Subject subject) {
        if (relatedSubjects == null) {
            relatedSubjects = new HashSet<>();
        }
        relatedSubjects.add(subject);
    }
}
