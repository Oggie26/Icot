package Project.example.Project_1.repository;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
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

    default User findByUsernameOrThrow(String username) {
        return findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }
    Optional<User> findUserById(String id);
}
