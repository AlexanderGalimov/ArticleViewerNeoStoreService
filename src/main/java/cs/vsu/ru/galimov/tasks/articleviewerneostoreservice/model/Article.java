package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@AllArgsConstructor
@Document(collection = "Articles")
@JsonDeserialize
@JsonSerialize
public class Article {
    @Id
    private String id;

    private Magazine magazine;

    private DepartmentMagazine departmentMagazine;

    private Archive archive;

    private DateArchive dateArchive;

    private PDFParams pdfParams;

    private List<String> authorIds;

    private String fullText;

    private List<String> keywords;

    private List<String> lpKeywords;

    private String annotation;

    private String uniqUIIDS3;
}

