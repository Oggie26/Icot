//package Project.example.Project_1.controller;
//
//import Project.example.Project_1.enums.EnumPaymentMethod;
//import Project.example.Project_1.response.ApiResponse;
//import Project.example.Project_1.response.OrderResponse;
//import Project.example.Project_1.service.OrderService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/orders")
//@Tag(name = "Order Controller")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Slf4j
//public class OrderController {
//
//    @Autowired
//    OrderService orderService;
//
//    @GetMapping("/history-order")
//    @PreAuthorize("hasAnyRole('CUSTOMER')")
//    public ApiResponse<List<OrderResponse>> getHistoryOrder(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
//        return ApiResponse.<OrderResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("Lấy danh sách mua hàng thành công")
//                .result(orderService.getOrdersByCustomer(page, size))
//                .build();
//    }
//
//
//
//    @GetMapping("/payment-callback")
//    public ApiResponse<String> handlePaymentCallback(@RequestParam Map<String, String> params) throws UnsupportedEncodingException {
////        boolean isValid = vnPayService.validateCallback(params);
////        if (!isValid) {
////            return ApiResponse.<String>builder()
////                    .code(HttpStatus.BAD_REQUEST.value())
////                    .message("Invalid Signature")
////                    .build();
////        }
//
//        String orderId = params.get("orderId");
//        String responseCode = params.get("vnp_ResponseCode");
//        boolean isPaid = "00" .equals(responseCode);
//
//        orderService.updateOrderStatus(Long.parseLong(orderId), isPaid);
//
//        if (isPaid) {
//            return ApiResponse.<String>builder()
//                    .code(HttpStatus.OK.value())
//                    .message("Thanh toán thành công")
//                    .build();
//        } else {
//            orderService.deleteOrder(Long.parseLong(orderId));
//            return ApiResponse.<String>builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message("Thanh toán thất bại")
//                    .build();
//        }
//    }
//
//    private String getClientIp(HttpServletRequest request) {
//        String clientIp = request.getHeader("X-Forwarded-For");
//        if (clientIp == null || clientIp.isEmpty()) {
//            clientIp = request.getRemoteAddr();
//        }
//        return clientIp;
//    }
//
//}
//
