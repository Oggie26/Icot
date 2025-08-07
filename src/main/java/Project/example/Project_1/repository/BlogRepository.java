package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Optional<Blog> findById(Long blogId);
    Optional<Blog> findBlogByBlogNameAndIsDeletedFalse(String blogName);
    Page<Blog> findByBlogNameContainingIgnoreCase(String blogName, Pageable pageable);
}
