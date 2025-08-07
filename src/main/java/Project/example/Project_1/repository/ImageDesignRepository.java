package Project.example.Project_1.repository;

import Project.example.Project_1.enity.BookOrder;
import Project.example.Project_1.enity.ImageDesign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageDesignRepository extends JpaRepository<ImageDesign,String> {

    List<ImageDesign> findByBookOrder(BookOrder bookOrder);
}
