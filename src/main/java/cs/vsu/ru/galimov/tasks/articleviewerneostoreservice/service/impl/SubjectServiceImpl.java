package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository.SubjectRepository;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public void updateSubject(Long id, Subject subject) {
        if (subjectRepository.existsById(id)) {
            subject.setId(id);
            subjectRepository.save(subject);
        }
    }

    @Override
    public Subject findByTitleAndAuthorsNamesContaining(String title, String author) {
        return subjectRepository.findByTitleAndAuthorsNamesContaining(title, author);
    }

    @Override
    public Subject findByTitleAndAuthorsNames(String title, List<String> authors) {
        return subjectRepository.findByTitleAndAuthorsNames(title, authors);
    }

    @Override
    public Subject findByTitleAndAnyAuthorInAuthorsNames(String title, List<String> authors) {
        return subjectRepository.findByTitleAndAnyAuthorInAuthorsNames(title, authors);
    }
}
