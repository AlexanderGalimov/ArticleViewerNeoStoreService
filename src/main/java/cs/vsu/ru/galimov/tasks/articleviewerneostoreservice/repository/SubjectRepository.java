package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.repository;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Subject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends Neo4jRepository<Subject, Long> {
    Subject findByTitleAndAuthorsNamesContaining(String title, String author);

    @Query("MATCH (s:Subject) " +
            "WHERE s.title = $title " +
            "AND ANY(author IN s.authorsNames WHERE author IN $authorsNames) " +
            "RETURN s")
    Subject findByTitleAndAuthorsNames(@Param("title") String title, @Param("authorsNames") List<String> authorsNames);

    @Query("""
    MATCH (s:Subject)
    WHERE s.title = $title AND ANY(author IN $authors WHERE author IN s.authorsNames)
    RETURN s
    """)
    Subject findByTitleAndAnyAuthorInAuthorsNames(String title, List<String> authors);
}