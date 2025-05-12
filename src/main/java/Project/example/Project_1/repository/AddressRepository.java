package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Address;
import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUser(User user);
}
