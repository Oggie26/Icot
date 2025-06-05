package Project.example.Project_1.request;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.enity.Fabric;
import Project.example.Project_1.enity.Image;
import Project.example.Project_1.enums.EnumSize;
import lombok.Data;

import java.util.List;

@Data
public class ProductUpdateRequest {
    String productName;
    Double price;
    String description;
    String imageThumbnail;
    EnumSize size;
    Category category;
    List<Image> images;
    Fabric fabric;
}
