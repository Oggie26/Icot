package Project.example.Project_1.request;

import lombok.Data;

@Data
public class PaymentBookOrderRequest {
    private long orderCode;
    private int amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private String signature;
    private long expiredAt;
}
