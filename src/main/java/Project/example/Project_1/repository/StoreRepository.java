package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findStoreById(long id);
}
