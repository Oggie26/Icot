package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Topping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToppingRepository extends JpaRepository<Topping ,Long> {
    Topping findToppingById(long id);
}
