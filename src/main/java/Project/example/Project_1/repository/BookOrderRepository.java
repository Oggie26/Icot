package Project.example.Project_1.repository;

import Project.example.Project_1.enity.BookOrder;
import Project.example.Project_1.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    Optional<BookOrder> findByIdAndIsDeletedFalse(Long id);
    List<BookOrder> findBookOrderByUser(User user);
    Page<BookOrder> findByUser_PhoneContainingIgnoreCase(String fabricName, PageRequest pageable);
    Optional<BookOrder> findBookOrderByUserIdAndIsDeletedFalse(String userId);

    @Query("SELECT SUM(b.totalPrice) FROM BookOrder b")
    Double getTotalBookOrderAmount();

    // Đếm tổng số đơn đặt may
    @Query("SELECT COUNT(b) FROM BookOrder b")
    Long countTotalBookOrders();

}
