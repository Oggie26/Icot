package Project.example.Project_1.repository;

import Project.example.Project_1.enity.TypePrint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypePrintRepository extends JpaRepository<TypePrint, Long> {

    Optional<TypePrint> findByIdAndIsDeletedFalse(long id);
    Optional<TypePrint> findByPrintNameAndIsDeletedFalse (String printName);
}
