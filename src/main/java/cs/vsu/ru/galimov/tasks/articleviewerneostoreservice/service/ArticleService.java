package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;

public interface ArticleService {

    Article findById(String id);

    Article findByUniqUIIDS3(String uniqUIIDS3);

    Article findByPdfParamsTitle(String title);
}
