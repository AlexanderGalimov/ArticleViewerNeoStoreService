package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.kafka;

import com.google.gson.Gson;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class NeoParse {

    private final ArticleService articleService;

    private final Gson gson;

    @Autowired
    public NeoParse(ArticleService articleService, Gson gson) {
        this.articleService = articleService;
        this.gson = gson;
    }

    @KafkaListener(topics = "${kafka.topic.name.for-graph-topic}", containerFactory = "kafkaListenerContainerFactory", concurrency = "${kafka.topic.partitions.for-graph-topic}")
    public void receive(String jsonArticle) {
        Article article = convertJsonToArticle(jsonArticle);

        String fullText = article.getFullText();
        List<String> authors = article.getPdfParams().getAuthors();
    }

    private Article convertJsonToArticle(String articleJson) {
        return gson.fromJson(articleJson, Article.class);
    }
}
