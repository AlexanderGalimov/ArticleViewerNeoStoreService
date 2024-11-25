package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.kafka;

import com.google.gson.Gson;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.relationship.RelationshipCreator;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NeoParse {

    private final ArticleService articleService;

    private final RelationshipCreator creator;

    private final Gson gson;

    private final Logger logger = LoggerFactory.getLogger(NeoParse.class);

    @Autowired
    public NeoParse(ArticleService articleService, RelationshipCreator creator, Gson gson) {
        this.articleService = articleService;
        this.creator = creator;
        this.gson = gson;
    }

    @KafkaListener(topics = "${kafka.topic.name.for-graph-topic}", containerFactory = "kafkaListenerContainerFactory", concurrency = "${kafka.topic.partitions.for-graph-topic}")
    public void receive(String jsonArticle) {
        try {
            String uuid = convertJsonToArticle(jsonArticle);
            Article article = articleService.findByUniqUIIDS3(uuid);
            if (article != null) {
                creator.createRelationShip(article);
                logger.info("Relationship created for article with uuid: " + uuid);
            } else {
                logger.info("Article with uuid: " + uuid + "is null");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String convertJsonToArticle(String articleJsonId) {
        return gson.fromJson(articleJsonId, String.class);
    }
}
