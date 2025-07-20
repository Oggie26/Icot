package Project.example.Project_1.controller;

import Project.example.Project_1.enity.Cart;
import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enums.EnumPaymentMethod;
import Project.example.Project_1.enums.EnumProcess;
import Project.example.Project_1.repository.OrderRepository;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.OrderResponse;
import Project.example.Project_1.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller")
@SecurityRequirement(name = "api")
@RequiredArgsConstructor
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/history-order")
    public ApiResponse<List<OrderResponse>> getHistoryOrder() {
        return ApiResponse.<List<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách mua hàng thành công")
                .result(orderService.getOrdersByCustomer())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách mua hàng thành công")
                .result(orderService.getOrderById(id))
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @RequestBody EnumProcess status , @RequestBody String image) {
        orderService.updateStatusOrder(id,status,image);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Thay đổi thành công")
                .build();
    }

    @DeleteMapping("/{cartId}")
    public ApiResponse<Void> deleteOrderById(@PathVariable Long cartId) {
        orderService.deleteCart(cartId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xoá thành công")
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<Order>> getAllOrders() {
        List<Order> list = orderRepository.findAll().stream()
                .filter(order -> !order.getIsDeleted())
                .toList();
        return ApiResponse.<List<Order>>builder()
                .code(HttpStatus.OK.value())
                .result(list)
                .build();
    }
//    @GetMapping("/payment-callback")
//    public ApiResponse<String> handlePaymentCallback(@RequestParam Map<String, String> params) throws UnsupportedEncodingException {
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

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }



}

