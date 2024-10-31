package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findProductById(long id);

}
