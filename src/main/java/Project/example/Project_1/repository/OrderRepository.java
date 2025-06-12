package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    Order findById(long id);
//    Order findByUser(User user);
}
