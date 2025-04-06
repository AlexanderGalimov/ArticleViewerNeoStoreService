package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.relationship.RelationshipCreator;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository.ArticleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.List;

@SpringBootApplication
@EnableKafka
public class ArticleViewerNeoStoreServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ArticleViewerNeoStoreServiceApplication.class, args);
        // MATCH (n) DETACH DELETE n;

        ArticleRepository articleService = context.getBean(ArticleRepository.class);
        RelationshipCreator creator = context.getBean(RelationshipCreator.class);

        List<Article> articleList = articleService.findAll();
        for (int i = 0; i < 300; i++) {
            creator.createRelationShip(articleList.get(i));
        }
    }
}
