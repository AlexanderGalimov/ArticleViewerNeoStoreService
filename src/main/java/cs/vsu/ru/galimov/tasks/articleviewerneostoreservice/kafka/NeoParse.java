package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.kafka;

import com.google.gson.Gson;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.RelationshipCreator;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.ArticleService;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.AuthorServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.SubjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NeoParse {

    private final ArticleService articleService;

    private final RelationshipCreator creator;

    private final Gson gson;

    @Autowired
    public NeoParse(ArticleService articleService, RelationshipCreator creator, Gson gson) {
        this.articleService = articleService;
        this.creator = creator;
        this.gson = gson;
    }

    @KafkaListener(topics = "${kafka.topic.name.for-graph-topic}", containerFactory = "kafkaListenerContainerFactory", concurrency = "${kafka.topic.partitions.for-graph-topic}")
    public void receive(String jsonArticle) {
        String articleId = convertJsonToArticle(jsonArticle);
        Article article = articleService.findById(articleId);
        try {
            creator.createRelationShip(article);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Error in parse:" + article.getPdfParams().getTitle());
        }
    }

    private String convertJsonToArticle(String articleJsonId) {
        return gson.fromJson(articleJsonId, String.class);
    }
}
