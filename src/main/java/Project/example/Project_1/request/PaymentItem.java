package Project.example.Project_1.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentItem {
    private String name;
    private int quantity;
    private int price;
}
