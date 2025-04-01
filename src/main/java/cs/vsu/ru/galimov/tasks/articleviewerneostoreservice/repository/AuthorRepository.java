package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
