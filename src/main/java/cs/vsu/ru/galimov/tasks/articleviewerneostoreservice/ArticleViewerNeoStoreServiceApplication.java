package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.ReferencesSeparator;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.RelationshipCreator;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.ArticleServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class ArticleViewerNeoStoreServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ArticleViewerNeoStoreServiceApplication.class, args);

        // MATCH (n) DETACH DELETE n;
        ArticleServiceImpl service = context.getBean(ArticleServiceImpl.class);
        List<Article> articles = service.findAll();
        //Article article = articles.get(0);

        RelationshipCreator creator = context.getBean(RelationshipCreator.class);
        //creator.createRelationShip(article);
        for (int i = 0; i < 300; i++) {
            creator.createRelationShip(articles.get(i));
        }
    }
}
