package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumSize;
import lombok.Data;

import java.util.List;

@Data
public class ProductCreateRequest {
    String productName;
    Double price;
    String description;
    String imageThumbnail;
    EnumSize size;
    String color;
    Long category;
    List<String> imagesUrls;
    Long fabric;
}
