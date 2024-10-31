package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(long id);
}
