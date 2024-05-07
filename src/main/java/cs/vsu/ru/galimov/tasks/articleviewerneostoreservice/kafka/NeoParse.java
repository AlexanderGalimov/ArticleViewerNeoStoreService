package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.kafka;

import com.google.gson.Gson;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.ArticleService;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.AuthorServiceImpl;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl.SubjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class NeoParse {

    private final ArticleService articleService;

    private final SubjectServiceImpl subjectService;

    private final AuthorServiceImpl authorService;

    private final Gson gson;

    @Autowired
    public NeoParse(ArticleService articleService, SubjectServiceImpl subjectService, AuthorServiceImpl authorService, Gson gson) {
        this.articleService = articleService;
        this.subjectService = subjectService;
        this.authorService = authorService;
        this.gson = gson;
    }

    @KafkaListener(topics = "${kafka.topic.name.for-graph-topic}", containerFactory = "kafkaListenerContainerFactory", concurrency = "${kafka.topic.partitions.for-graph-topic}")
    public void receive(String jsonArticle) {
        String articleId = convertJsonToArticle(jsonArticle);
        Article article = articleService.findById(articleId);
        List<String> authorsNames = new ArrayList<>();
        for (String authorsIds: article.getAuthorIds()) {
             Author author = authorService.findById(authorsIds);
             authorsNames.add(author.getName());
        }
        Subject subject = new Subject(article.getPdfParams().getTitle(), authorsNames, article.getDepartmentMagazine().getName());
        subjectService.createSubject(subject);
    }

    private String convertJsonToArticle(String articleJsonId) {
        return gson.fromJson(articleJsonId, String.class);
    }
}
