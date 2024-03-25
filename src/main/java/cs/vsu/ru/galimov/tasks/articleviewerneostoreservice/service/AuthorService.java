package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.service;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Component
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author updateAuthor(Long id, Author author) {
        if (authorRepository.existsById(id)) {
            author.setId(id);
            return authorRepository.save(author);
        }
        return null;
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public void addRelatedAuthors(String mainAuthorName, Set<String> relatedAuthorNames) {
        Author mainAuthor = authorRepository.findByName(mainAuthorName).orElse(null);

        if (mainAuthor == null) {
            mainAuthor = new Author(mainAuthorName, new HashSet<>());
            authorRepository.save(mainAuthor);
        }

        for (String relatedAuthorName : relatedAuthorNames) {
            Author relatedAuthor = authorRepository.findByName(relatedAuthorName).orElse(null);
            if (relatedAuthor == null) {
                relatedAuthor = new Author(relatedAuthorName, new HashSet<>());
                authorRepository.save(relatedAuthor);
                mainAuthor.addRelatedAuthor(relatedAuthor);
            }
        }

        authorRepository.save(mainAuthor);
    }
}
