package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.impl;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository.AuthorRepository;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    public Author findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
