package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher , Long> {
    Voucher findVoucherById(long id);
}
