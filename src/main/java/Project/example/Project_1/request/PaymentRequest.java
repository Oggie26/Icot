package Project.example.Project_1.request;

import lombok.Getter;
import lombok.Setter;
import vn.payos.type.PaymentData;
import java.util.List;

@Getter
@Setter
public class PaymentRequest {
    private long orderCode;
    private int amount;
    private String description;
    private List<Item> items;
    private String cancelUrl;
    private String returnUrl;
    private String signature;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private String buyerAddress;
    private long expiredAt;
}
