package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends Neo4jRepository<Subject, Long> {
    List<Subject> findByAuthorsNamesContaining(String authorName);

    List<Subject> findByDepartmentMagazineName(String departmentMagazineName);

    Subject findByTitleContaining(String title);
}