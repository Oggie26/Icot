package Project.example.Project_1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    Long cartId;
    String username;
    List<CartItemResponse> items;
    Double totalPrice;
}
