package Project.example.Project_1.repository;

import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findById(long id);

//    @Query(value = "SELECT * FROM account c WHERE c.id = :param OR c.phone like concat('%',:param,'%') "  , nativeQuery = true)
//    List<User> findByStaff(@Param("param") String param);
}
