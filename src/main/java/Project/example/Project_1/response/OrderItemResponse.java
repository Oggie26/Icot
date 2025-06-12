package Project.example.Project_1.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {
    Long id;
    String productId;
    String productName;
    Integer quantity;
    Double price;
    Double totalPrice;
    String thumbnailProduct;
}
