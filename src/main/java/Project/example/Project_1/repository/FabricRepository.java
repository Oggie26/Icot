package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Fabric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FabricRepository extends JpaRepository<Fabric, Long> {
    Optional<Fabric> findByFabricName(String fabricName);
    Optional<Fabric> findByIdAndIsDeletedFalse(Long id);
    Page<Fabric> findByFabricNameContainingIgnoreCase(String fabricName, PageRequest pageable);
}
