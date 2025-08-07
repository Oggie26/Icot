package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByIdAndIsDeletedFalse(Long id);
    Optional<Category> findByCategoryNameAndIsDeletedFalse(String categoryName);

}
