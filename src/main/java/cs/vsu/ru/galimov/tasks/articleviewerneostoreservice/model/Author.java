package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Node("Author")
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;

    @Relationship(type = "RELATED_TO")
    private Set<Author> relatedAuthors;

    public Author(String name, Set<Author> relatedAuthors) {
        this.name = name;
        this.relatedAuthors = relatedAuthors;
    }

    public Author() {
    }

    public void addRelatedAuthor(Author author) {
        if (relatedAuthors == null) {
            relatedAuthors = new HashSet<>();
        }
        relatedAuthors.add(author);
    }
}
