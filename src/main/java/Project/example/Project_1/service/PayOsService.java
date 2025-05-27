package Project.example.Project_1.service;

import Project.example.Project_1.enity.BookOrder;
import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enity.OrderItem;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumProcess;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.BookOrderRepository;
import Project.example.Project_1.repository.OrderItemRepository;
import Project.example.Project_1.repository.OrderRepository;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.PaymentItem;
import Project.example.Project_1.request.PaymentRequest;
import Project.example.Project_1.response.PaymentBookOrder;
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

    @Autowired
    BookOrderRepository bookOrderRepository;

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
    public PaymentOrderResponse paymentOrder(Long orderId, String isAddress) throws Exception {
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

    public PaymentBookOrder paymentBookOrder(Long bookOrderId, String isAddress) throws Exception {
        User user = getAuthenticatedUser();

        BookOrder bookOrder = bookOrderRepository.findById(bookOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Double price = bookOrder.getTotalPrice();
        Double amount = Double.valueOf(price);
        String url = createPaymentLinkByOrderCode(bookOrderId, isAddress);
        bookOrderRepository.save(bookOrder);

        return PaymentBookOrder.builder()
                .dateTime(LocalDateTime.now())
                .price(amount)
                .bookOrderId(bookOrderId)
                .status(EnumBookOrder.PAYMENT)
                .build();
    }

//    public String createPaymentLinkByBookOrder(Long bookOrderId, String isAddress) throws Exception {
//        // Step 1: Fetch the BookOrder
//        BookOrder bookOrder = bookOrderRepository.findById(bookOrderId)
//                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));
//
//        // Step 2: Convert total price to int (in smallest currency unit if required)
//        double totalPrice = bookOrder.getTotalPrice();
//        if (totalPrice <= 0) {
//            throw new AppException(ErrorCode.INVALID_TOTAL_PRICE);
//        }
//        int amount = (int) Math.round(totalPrice);
//
//        // Step 3: Prepare Payment Items (ensure orderItems exist)
//        List<OrderItem> orderItems = bookOrder.getOrderItems();
//        if (orderItems == null || orderItems.isEmpty()) {
//            throw new AppException(ErrorCode.ORDER_ITEMS_NOT_FOUND);
//        }
//
//        List<PaymentItem> items = orderItems.stream()
//                .map(orderItem -> PaymentItem.builder()
//                        .name(orderItem.getBook().getTitle())
//                        .quantity(orderItem.getQuantity())
//                        .price((int) Math.round(orderItem.getPrice()))
//                        .build())
//                .collect(Collectors.toList());
//
//        // Step 4: Set payment link expiration (1 hour from now in seconds)
//        int expiredAt = (int) (System.currentTimeMillis() / 1000L + 3600);
//
//        // Step 5: Build PaymentData (convert orderCode to String if required by PayOS)
//        PaymentData paymentData = PaymentData.builder()
//                .orderCode(Long.valueOf(String.valueOf(bookOrder.getId())))
//                .amount(amount)
//                .description("Thanh toán đơn hàng #" + bookOrder.getId())
//                .returnUrl("https://localhost:3000/success")
//                .cancelUrl("https://localhost:3000/cancel")
//                .items(items)
//                .expiredAt(expiredAt)
//                .build();
//
//        // Step 6: Call PayOS and get the payment link
//        CheckoutResponseData response = payOS.createPaymentLink(paymentData);
//
//        // Step 7: Return the checkout URL
//        if (response == null || response.getCheckoutUrl() == null) {
//            throw new AppException(ErrorCode.PAYMENT_LINK_CREATION_FAILED);
//        }
//
//        return response.getCheckoutUrl();
//    }



    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrThrow(username);
    }


}
