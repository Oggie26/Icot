package Project.example.Project_1.repository;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findByUsername(String username);

    Optional<User> findUserByIdAndIsDeletedFalse(String id);
    Optional<User> findUserById(String id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByEmailAndIsDeletedFalse(String email);
    Optional<User> findUserByPhoneAndIsDeletedFalse(String phone);
    Optional<User> findByIdAndIsDeletedFalse(String id);
    User findUserByPhone(String phone);

    default User findByUsernameOrThrow(String username) {
        return findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design"));
    }
//    Optional<User> findUserById(String id);

    boolean existsByEmailAndIdNot(String email, String id);
    boolean existsByPhoneAndIdNot(String phone, String id);
}
