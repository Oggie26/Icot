package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Design;
import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignRepository extends JpaRepository<Design, Long> {
    Optional<Design> findByIdAndIsDeletedFalse(Long id);
    Optional<Design> findDesignByUser(User user);
}
