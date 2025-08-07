package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumSize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    EnumSize size;

}
