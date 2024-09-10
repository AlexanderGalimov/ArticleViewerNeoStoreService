package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model;

import lombok.Data;

@Data
public class DepartmentMagazine {
    private String name;

    private String url;

    public DepartmentMagazine(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public DepartmentMagazine() {
    }
}

