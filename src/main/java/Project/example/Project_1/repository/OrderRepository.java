package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    Order findById(long id);
}
