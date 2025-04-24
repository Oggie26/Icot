package Project.example.Project_1.repository;

import Project.example.Project_1.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);
    User findByUsername(String username);
    Optional<User> findUserByIdAndIsDeletedFalse(String id);
    Optional<User> findUserByEmail(String email);
    User findUserById(String id);
    List<User> findAllByIsDeletedFalse();
    Page<User> findAllUserPaging(Pageable pageable);
}
