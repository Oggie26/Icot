package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    Order findById(long id);
    List<Order> findOrderByUser(User user);
}
