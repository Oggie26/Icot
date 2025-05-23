package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Cart;
import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(String userId);
    Optional<Cart> findByUser(User user);
}
