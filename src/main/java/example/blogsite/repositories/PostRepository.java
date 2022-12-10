package example.blogsite.repositories;

import example.blogsite.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p from Post p WHERE p.category.id = ?1")
    List<Post> findAllByCategoryId(long categoryId);

    List<Post> findPostsByUserId(Long id);

    Optional<Post> findByUniqueTitle(String title);
}
