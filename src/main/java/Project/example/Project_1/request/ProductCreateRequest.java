package Project.example.Project_1.request;

import Project.example.Project_1.enity.Image;
import Project.example.Project_1.enity.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductCreateRequest {
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
