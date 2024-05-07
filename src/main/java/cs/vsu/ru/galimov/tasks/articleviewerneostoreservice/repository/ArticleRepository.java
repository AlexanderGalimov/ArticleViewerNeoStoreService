package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    Article findByPdfParamsTitle(String title);

    Article findByUniqUIIDS3(String uniqUIIDS3);

    List<Article> findByAuthorIdsContaining(String authorId);
}
