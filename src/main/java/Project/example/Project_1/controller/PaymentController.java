package Project.example.Project_1.controller;

import Project.example.Project_1.request.PaymentRequest;
import Project.example.Project_1.service.PayOsService;
import io.swagger.v3.oas.annotations.Webhook;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;
import vn.payos.type.WebhookData;

import java.util.HashMap;
import java.util.Map;

@Validated
@SecurityRequirement(name = "api")
@CrossOrigin("*")
@Tag(name = "Payment Controller")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PayOS payOS;

    @Autowired
    PayOsService  payOsService;

//    @PostMapping("/create")
//    public CheckoutResponseData createPayment(@RequestBody PaymentData paymentData) throws Exception {
//        return payOS.createPaymentLink(paymentData);

//        PaymentData paymentData = new PaymentData();
//        paymentData.setOrderCode(123456);
//        paymentData.setAmount(50000);
//        paymentData.setDescription("Thanh toán đơn hàng 123456");
//        paymentData.setReturnUrl("https://yourwebsite.com/return");
//        paymentData.setCancelUrl("https://yourwebsite.com/cancel");
//        paymentData.setExpiredAt(System.currentTimeMillis() + 3600000); // 1 giờ sau
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestParam long orderCode, @RequestParam int amount) {
        try {
            // Gọi service để build request + tính signature
            PaymentRequest request = payOsService.buildPaymentRequest(orderCode, amount);

            // Gửi lên PayOS
            CheckoutResponseData response = payOS.createPaymentLink(request);
            Map<String, String> result = new HashMap<>();
            result.put("checkoutUrl", response.getCheckoutUrl());
            result.put("qrCode", response.getQrCode());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Lỗi tạo thanh toán: " + e.getMessage());
        }
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Webhook webhook) throws Exception {
        WebhookData verifiedData = payOS.verifyPaymentWebhookData((vn.payos.type.Webhook) webhook);

        if (verifiedData == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID SIGNATURE");
        }

        // Xác thực thành công, xử lý đơn hàng
        System.out.println("✅ Thanh toán thành công đơn hàng: " + verifiedData.getOrderCode());
        return ResponseEntity.ok("OK");
    }
}
