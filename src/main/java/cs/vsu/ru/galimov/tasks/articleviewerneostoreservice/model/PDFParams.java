package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;

import lombok.Data;


@Data
public class PDFParams {
    private String link;

    private String title;

    public PDFParams(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public PDFParams() {
    }
}
