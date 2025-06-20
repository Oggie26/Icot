package Project.example.Project_1.controller;

import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enums.EnumPaymentMethod;
import Project.example.Project_1.request.PaymentInitRequest;
import Project.example.Project_1.request.PaymentRequest;
import Project.example.Project_1.response.*;
import Project.example.Project_1.service.OrderService;
import Project.example.Project_1.service.PayOsService;
import Project.example.Project_1.service.PaymentService;
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

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

//    @PostMapping()
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Payment", description = "API get payment")
//    public ApiResponse<PaymentOrderResponse> paymentOrder(
//            @RequestParam @Valid Long orderId,
//            HttpServletRequest http
//    ) throws Exception {
//        String clientIp = getClientIp(http);
//        PaymentOrderResponse order = payOsService.paymentOrder(orderId, clientIp);
//
//        return ApiResponse.<PaymentOrderResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("Payment successfully")
//                .result(order)
//                .build();
//    }


    @PostMapping
    @Operation(summary = "Tạo đơn hàng", description = "Tạo một đơn hàng mới từ giỏ hàng và địa chỉ đã chọn")
    public ResponseEntity<ApiResponse<OrderResponse>> paymentOrder(
            @RequestParam Long cartId,
            @RequestParam Long addressId,
            @RequestParam EnumPaymentMethod paymentMethod
    ) {
        OrderResponse response = orderService.createOrder(cartId, addressId, paymentMethod);
        return ResponseEntity.ok(
                ApiResponse.<OrderResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Tạo đơn hàng thành công")
                        .result(response)
                        .build()
        );
    }

    @PostMapping("/payOs")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Tạo thanh toán và trả về link PayOS")
    public ApiResponse<PaymentInitResponse> initPayment(@Valid @RequestBody PaymentInitRequest request) {
        PaymentInitResponse response = payOsService.createPayment(request.getOrderCode(), request.getAmount());
        return ApiResponse.<PaymentInitResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Khởi tạo thanh toán thành công")
                .result(response)
                .build();
    }

//    @PostMapping("/cartId")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Thanh toán tiền mặt")
//    public ApiResponse<PaymentOrderResponse> checkoutPayment(@RequestParam @Valid Long cartId, @RequestBody Long addressId){
//         return ApiResponse.<PaymentOrderResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("Thanh toán thành công")
//                .result(paymentService.paymentCode(cartId))
//                .build();
//    }



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

    @PostMapping("/init")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Khởi tạo thanh toán", description = "Tạo thông tin thanh toán cho đơn hàng")
    public ApiResponse<PaymentRequest> initPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentRequest paymentRequest = payOsService.buildPaymentRequest(request.getOrderCode(), request.getAmount());
        return ApiResponse.<PaymentRequest>builder()
                .code(HttpStatus.OK.value())
                .message("Khởi tạo thanh toán thành công")
                .result(paymentRequest)
                .build();
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
