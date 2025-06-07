package Project.example.Project_1.request;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.enity.Fabric;
import Project.example.Project_1.enity.Image;
import Project.example.Project_1.enums.EnumSize;
import lombok.Data;

import java.util.List;

@Data
public class ProductUpdateRequest {
    String id;
    String productName;
    Double price;
    String description;
    String imageThumbnail;
    List<SizeRequest> sizes;
    Long categoryId;
    String color;
    Long fabricId;
    Long typePrintId;
    List<ImageRequest> images;
}
