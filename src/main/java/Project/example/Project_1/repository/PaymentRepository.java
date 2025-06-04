package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
