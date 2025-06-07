package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order); // lấy các item theo đơn hàng
}
