package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;


import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;

public interface AuthorService {

    Author findById(String id);
}
