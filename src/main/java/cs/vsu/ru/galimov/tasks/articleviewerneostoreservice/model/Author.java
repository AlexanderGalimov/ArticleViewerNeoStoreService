package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Authors")
public class Author {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
}
