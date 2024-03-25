package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class ArticleService implements Served<Article> {

    private final ArticleRepository articleRepository;

    @Autowired
    private ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void insert(Article article) {
        articleRepository.insert(article);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public void delete(String id) {
        Article archive = findById(id);
        articleRepository.delete(archive);
    }

    @Override
    public Article findById(String id) {
        Optional<Article> optionalArchive = articleRepository.findById(id);
        return optionalArchive.orElse(null);
    }

    @Override
    public Article findByUniqUIIDS3(String uniqUIIDS3) {
        return articleRepository.findByUniqUIIDS3(uniqUIIDS3);
    }

    @Override
    public void update(Article object) {
        articleRepository.save(object);
    }
}