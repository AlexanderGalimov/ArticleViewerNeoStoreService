package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArticleViewerNeoStoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleViewerNeoStoreServiceApplication.class, args);
        // MATCH (n) DETACH DELETE n;
    }
}
