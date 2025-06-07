package Project.example.Project_1.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PaymentData {
    private String orderCode;
    private int amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private List<PaymentItem> items;
    private int expiredAt;
}
