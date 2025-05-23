package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    public void deleteImagesByProductId(String productId);
}
