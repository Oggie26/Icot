package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByNameAndIsDeletedFalse (String name);
    Optional<Tag> findByIdAndIsDeletedFalse(Long id);
}
