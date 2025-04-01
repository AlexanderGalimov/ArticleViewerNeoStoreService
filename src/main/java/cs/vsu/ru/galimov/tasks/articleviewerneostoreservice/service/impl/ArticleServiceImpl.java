package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository.ArticleRepository;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article findById(String id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public Article findByUniqUIIDS3(String uniqUIIDS3) {
        return articleRepository.findByUniqUIIDS3(uniqUIIDS3);
    }

    @Override
    public Article findByPdfParamsTitle(String title) {
        return articleRepository.findByPdfParamsTitle(title);
    }
}