package Project.example.Project_1.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vn.payos.type.PaymentData;
import java.util.List;

@Getter
@Setter
@Data
public class PaymentRequest {

    private long orderCode;
    private int amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private String signature;
    private long expiredAt;

}
