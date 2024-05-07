package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;


import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleService {
    Article insert(Article article);

    List<Article> findAll();

    void delete(String id);

    Article findById(String id);

    Article findByUniqUIIDS3(String uniqUIIDS3);

    Article update(Article object);

    List<Article> findByAuthorIdsContaining(String authorId);

    Article findByPdfParamsTitle(String title);
}
