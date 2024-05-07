package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Articles")
@JsonDeserialize
@JsonSerialize
public class Article {
    @Id
    private String id;

    private Magazine magazine;

    private DepartmentMagazine departmentMagazine;

    private DateArchive dateArchive;

    private PDFParams pdfParams;

    private List<String> authorIds;

    private String fullText;

    private String uniqUIIDS3;

    public Article(Magazine magazine, DepartmentMagazine departmentMagazine, DateArchive dateArchive, PDFParams pdfParams) {
        this.magazine = magazine;
        this.departmentMagazine = departmentMagazine;
        this.dateArchive = dateArchive;
        this.pdfParams = pdfParams;
    }

    public Article() {
    }
}

