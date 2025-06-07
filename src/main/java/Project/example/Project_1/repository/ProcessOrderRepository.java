package Project.example.Project_1.repository;

import Project.example.Project_1.enity.ProcessOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessOrderRepository extends JpaRepository<ProcessOrder, Long> {
    Optional<ProcessOrder> findById(Long id);
}
