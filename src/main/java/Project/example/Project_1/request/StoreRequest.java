package Project.example.Project_1.request;

import lombok.Data;

@Data
public class StoreRequest {
    private String storeName;
    private String image;
    private String address;
    private Float revenue;
    private Boolean status;
}
