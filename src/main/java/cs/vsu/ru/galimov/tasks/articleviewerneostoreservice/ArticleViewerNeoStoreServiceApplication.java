package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.RelationshipCreator;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.SubjectServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ArticleViewerNeoStoreServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ArticleViewerNeoStoreServiceApplication.class, args);

        // MATCH (n) DETACH DELETE n;

        RelationshipCreator creator = context.getBean(RelationshipCreator.class);
        creator.createRelationship("geograph");
        }
}
