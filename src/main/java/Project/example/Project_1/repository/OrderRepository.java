package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    Order findById(long id);
    List<Order> findOrderByUser(User user);

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double getTotalAmountAllOrders();

    // Đếm tổng số đơn hàng
    @Query("SELECT COUNT(o) FROM Order o")
    Long getTotalOrderCount();
}
