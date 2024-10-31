package Project.example.Project_1.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private Float price;
    private Boolean status;
    private String image;
    private int purchases;
    private String description;
}
