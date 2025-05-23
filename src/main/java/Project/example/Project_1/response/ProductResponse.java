package Project.example.Project_1.response;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.enity.Fabric;
import Project.example.Project_1.enity.Feedback;
import Project.example.Project_1.enity.Image;
import Project.example.Project_1.enums.EnumSize;
import Project.example.Project_1.enums.EnumStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProductResponse {
    String id;
    String productName;
    Double price;
    String description;
    String imageThumbnail;
    EnumSize size;
    EnumStatus status;
    CategoryResponse category;
    List<Feedback> feedbacks;
    List<Image> images;
    FabricResponse fabric;
    Date createdAt;
    Date updatedAt;
    Boolean isDeleted;
}
