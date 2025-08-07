package Project.example.Project_1.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentInitRequest {
    @NotNull(message = "Mã đơn hàng không được để trống")
    private Long orderCode;

    @Min(value = 1000, message = "Số tiền tối thiểu là 1.000 VND")
    private int amount;
}
