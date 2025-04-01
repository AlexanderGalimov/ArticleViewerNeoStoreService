package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectService {
    Subject createSubject(Subject subject);

    void updateSubject(Long id, Subject subject);

    Subject findByTitleAndAuthorsNamesContaining(String title, String author);

    Subject findByTitleAndAuthorsNames(@Param("title") String title, @Param("authorsNames") List<String> authorsNames);
}
