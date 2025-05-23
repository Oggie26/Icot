package Project.example.Project_1.response;

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
    String image;
    Double price;
    int quantity;
    Double totalItemPrice;
}
