package Project.example.Project_1.response;

import lombok.Data;

@Data
public class PaymentInitResponse {
    private String checkoutUrl;
    private Long orderCode;
    private int amount;
    private String status;
    private String paymentLinkId; // Added to match PayOS's PaymentResponse
}
