package Project.example.Project_1.response;

import Project.example.Project_1.enity.*;
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
    List<Size> sizes;
    EnumStatus status;
    Category category;
    Fabric fabric;
    TypePrint typePrint;
    List<Feedback> feedbacks;
    List<Image> images;
    Date createdAt;
    Date updatedAt;
}
