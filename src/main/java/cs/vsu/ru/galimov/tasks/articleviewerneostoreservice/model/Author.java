package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Authors")
public class Author {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public Author() {
    }
}
