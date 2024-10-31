package Project.example.Project_1.request;

import lombok.Data;

@Data
public class ToppingRequest {
    private String name;
    private Float price;
    private int quantity;
    private Boolean status;

}
