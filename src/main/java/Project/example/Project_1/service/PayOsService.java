package Project.example.Project_1.service;

import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enity.OrderItem;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumProcess;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.OrderItemRepository;
import Project.example.Project_1.repository.OrderRepository;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.PaymentRequest;
import Project.example.Project_1.response.PaymentOrderResponse;
import Project.example.Project_1.util.SignatureUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayOsService {
    @Value("${payos.checksumKey}")
    private String checksumKey;

    @Autowired
    PayOS payOS;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserRepository userRepository;

    public PaymentRequest buildPaymentRequest(long orderCode, int amount) {
        String description = "Thanh toán đơn hàng #" + orderCode;
        String returnUrl = "https://localhost:3000/return";
        String cancelUrl = "https://localhost:3000/cancel";

        String rawData = orderCode + "" + amount + description + returnUrl + cancelUrl;
        String signature = SignatureUtil.hmacSHA256(rawData, checksumKey); // checksumKey là biến cấu hình

        PaymentRequest req = new PaymentRequest();
        req.setOrderCode(orderCode);
        req.setAmount(amount);
        req.setDescription(description);
        req.setReturnUrl(returnUrl);
        req.setCancelUrl(cancelUrl);
        req.setSignature(signature);
        req.setExpiredAt(System.currentTimeMillis() + 3600_000);

        return req;
    }


    public String createPaymentLinkByOrderCode(Long orderCode, String isAddress) throws Exception {
        Order order = orderRepository.findById(orderCode)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);

        List<ItemData> items = orderItems.stream()
                .map(item -> ItemData.builder()
                        .name(item.getProduct().getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice().intValue())
                        .build())
                .collect(Collectors.toList());

        int amount = orderItems.stream()
                .mapToInt(i -> i.getTotalPrice().intValue())
                .sum();

        PaymentData paymentData = PaymentData.builder()
                .orderCode(order.getId())
                .amount(amount)
                .description("Thanh toán đơn hàng #" + order.getId())
                .returnUrl("https://localhost:3000/success")
                .cancelUrl("https://localhost:3000/cancel")
                .items(items)
                .expiredAt((int) (System.currentTimeMillis() + 3600_000))
                .build();

        CheckoutResponseData response = payOS.createPaymentLink(paymentData);
        return response.getCheckoutUrl();
    }

    @Transactional
    public PaymentOrderResponse paymentBookingOrder(Long orderId, String isAddress) throws Exception {
        User user = getAuthenticatedUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Double price = order.getTotalAmount();
        Double amount = Double.valueOf(price);
        String url = createPaymentLinkByOrderCode(orderId, isAddress);
        orderRepository.save(order);

        return PaymentOrderResponse.builder()
                .orderId(orderId)
                .dateTime(LocalDateTime.now())
                .price(amount)
                .status(EnumProcess.CONFIRMED)
                .build();
    }

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrThrow(username);
    }


}
