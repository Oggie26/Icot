package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumSize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    String productId;
    String productName;
    String color;
    @Enumerated(EnumType.STRING)
    EnumSize size;
    String image;
    Double price;
    int quantity;
    Double totalItemPrice;
}
