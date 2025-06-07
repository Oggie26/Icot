package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByCodeAndIsDeletedFalse(String code);
    Optional<Voucher> findByIdAndIsDeletedFalse(Long id);
}
