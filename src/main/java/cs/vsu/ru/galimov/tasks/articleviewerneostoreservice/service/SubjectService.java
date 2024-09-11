package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SubjectService {
    Subject createSubject(Subject subject);

    List<Subject> getAllSubjects();

    Subject getSubjectById(Long id);

    Subject updateSubject(Long id, Subject subject);

    void deleteSubject(Long id);

    List<Subject> findByAuthorsNamesContaining(String authorName);

    List<Subject> findByDepartmentMagazineName(String departmentMagazineName);

    Subject findByTitleContaining(String title);
}
