package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.kafka;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.relationship.RelationshipCreator;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NeoParse {

    private final ArticleService articleService;

    private final RelationshipCreator creator;

    @KafkaListener(topics = "${kafka.graph-topic.name}",
            containerFactory = "kafkaListenerContainerFactory",
            concurrency = "${kafka.graph-topic.partitions}")
    public void receive(String uniqueName) {
        try {
            Article article = articleService.findByUniqUIIDS3(uniqueName);
            if (article != null) {
                creator.createRelationShip(article);
                log.info("Relationship created for article with uuid: {}", uniqueName);
            } else {
                log.info("Article with uuid: {} is null", uniqueName);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
