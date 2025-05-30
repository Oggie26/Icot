package Project.example.Project_1.controller;

import Project.example.Project_1.request.PaymentRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.PaymentBookOrder;
import Project.example.Project_1.response.PaymentOrderResponse;
import Project.example.Project_1.service.PayOsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Webhook;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;
import vn.payos.type.WebhookData;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Payment", description = "API get payment ")
    public ApiResponse<PaymentOrderResponse> paymentOrder(@RequestParam @Valid Long orderId ,
                                                                 HttpServletRequest http) throws Exception {
        String clientIp = getClientIp(http);
        PaymentOrderResponse order = payOsService.paymentOrder(orderId,clientIp);
        return ApiResponse.<PaymentOrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Payment successfully")
                .result(order)
                .build();
    }

//    @PostMapping("/paymentBookOrder/{bookOrderId}")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Payment", description = "API get payment ")
//    public ApiResponse<PaymentOrderResponse> paymentBookOrder(@PathVariable @Valid Long bookOrderId ,
//                                                          HttpServletRequest http) throws Exception {
//        String clientIp = getClientIp(http);
//        PaymentBookOrder bookOrder = payOsService.paymentBookOrder(bookOrderId,clientIp);
//        return ApiResponse.<PaymentBookOrder>builder()
//                .code(HttpStatus.OK.value())
//                .message("Payment successfully")
//                .result(bookOrder)
//                .build();
//    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
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
