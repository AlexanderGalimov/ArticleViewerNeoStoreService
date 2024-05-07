package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.RelationshipCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ArticleViewerNeoStoreServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ArticleViewerNeoStoreServiceApplication.class, args);
        /*ReferencesSeparator separator = context.getBean(ReferencesSeparator.class);
        ArticleService articleService = context.getBean(ArticleService.class);
        SubjectServiceImpl subjectServiceImpl = context.getBean(SubjectServiceImpl.class);

        String authors = articleService.findByUniqUIIDS3("2f2e92db-e557-4f61-a4df-d8a3c237a626").getFullText();
        subjectServiceImpl.addRelatedAuthors("Гусев А. П", separator.getRelatedAuthors(authors));*/

        // MATCH (n) DETACH DELETE n;

        RelationshipCreator creator = context.getBean(RelationshipCreator.class);
        creator.createRelationship("geograph");
    }

}
