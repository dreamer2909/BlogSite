package example.blogsite.repositories;

import example.blogsite.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findAllByTitle(String title);
}
