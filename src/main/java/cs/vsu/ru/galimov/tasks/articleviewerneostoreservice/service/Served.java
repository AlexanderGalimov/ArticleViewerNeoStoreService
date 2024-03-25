package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;

import java.util.List;

public interface Served<T> {
    void insert(T object);

    List<T> findAll();

    void delete(String id);

    T findById(String id);

    T findByUniqUIIDS3(String uniqUIIDS3);

    void update(T object);
}
