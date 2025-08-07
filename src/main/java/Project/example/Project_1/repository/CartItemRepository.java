package Project.example.Project_1.repository;

import Project.example.Project_1.enity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void delete(CartItem cartItem);
}
