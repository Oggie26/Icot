package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    Blog findBlogById(long id);
}
