package Project.example.Project_1.service;

import Project.example.Project_1.request.PaymentRequest;
import Project.example.Project_1.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayOsService {
    @Value("${payos.checksumKey}")
    private String checksumKey;

    public PaymentRequest buildPaymentRequest(long orderCode, int amount) {
        String description = "Thanh toán đơn hàng #" + orderCode;
        String returnUrl = "https://localhost:8080/return";
        String cancelUrl = "https://localhost:8080/cancel";

        String rawData = orderCode + "" + amount + description + returnUrl + cancelUrl;
        String signature = SignatureUtil.hmacSHA256(rawData, checksumKey); // checksumKey là biến cấu hình

        PaymentRequest req = new PaymentRequest();
        req.setOrderCode(orderCode);
        req.setAmount(amount);
        req.setDescription(description);
        req.setReturnUrl(returnUrl);
        req.setCancelUrl(cancelUrl);
        req.setSignature(signature);
        req.setExpiredAt(System.currentTimeMillis() + 3600_000); // hết hạn sau 1 giờ

        return req;
    }
}
