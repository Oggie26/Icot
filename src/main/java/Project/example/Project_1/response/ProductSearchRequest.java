package Project.example.Project_1.response;

import lombok.Data;

import java.util.Date;

@Data
public class ProductSearchRequest {
    String productName;
    Double minPrice;
    Double maxPrice;
    String categoryId;
    String size;
    String status;
    Boolean isDeleted;
    Integer page;
    private Date fromCreatedAt;
    private Date toCreatedAt;
}
