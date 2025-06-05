package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Product;
import Project.example.Project_1.enity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size,Long> {
    List<Size> findAllByProductId(String productId);
}
