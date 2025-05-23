package Project.example.Project_1.repository;

import Project.example.Project_1.enity.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {

}
