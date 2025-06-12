//package Project.example.Project_1.service;
//
//import Project.example.Project_1.enity.*;
//import Project.example.Project_1.enums.*;
//import Project.example.Project_1.exception.AppException;
//import Project.example.Project_1.repository.*;
//import Project.example.Project_1.response.OrderItemResponse;
//import Project.example.Project_1.response.OrderResponse;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
//
//
//@Service
//public class OrderService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private AddressRepository addressRepository;
//
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Transactional
//    public OrderResponse createOrder(Long cartId, Long addressId, EnumPaymentMethod paymentMethod) {
//        User user = getAuthenticatedUser();
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
//        Address address = addressRepository.findById(addressId)
//                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
//        if (paymentMethod == null) {
//            throw new AppException(ErrorCode.INVALID_PAYMENT_METHOD);
//        }
//        Order order = buildOrder(cart, address, paymentMethod);
//        order = orderRepository.save(order);
//        List<OrderItem> orderItems = createOrderItemsFromCart(cart, order);
//        orderItems.forEach(orderItem -> {
//            Product product = productRepository.findByIdAndIsDeletedFalse(orderItem.getProduct().getId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
//        });
//        orderItemRepository.saveAll(orderItems);
//        order.setOrderItems(orderItems);
//        orderRepository.save(order);
//        if (order.getPaymentMethod() == EnumPaymentMethod.COD) {
//            clearCart(cart);
//        }
//
//        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
//                .map(item -> OrderItemResponse.builder()
//                        .id(item.getId())
//                        .productId(item.getProduct().getId())
//                        .productName(item.getProduct().getProductName())
//                        .quantity(item.getQuantity())
//                        .price(item.getPrice())
//                        .totalPrice(item.calculateTotalPrice())
//                        .thumbnailProduct(item.getProduct().getImageThumbnail())
//                        .build())
//                .toList();
//        return OrderResponse.builder()
//                .orderId(order.getId())
//                .imageOrderSuccess(order.getImageOrderSuccess())
//                .address(order.getAddress())
//                .paymentMethod(order.getPaymentMethod())
//                .orderDate(order.getOrderDate())
//                .totalAmount(order.getTotalAmount())
//                .paymentStatus(order.getPaymentStatus())
//                .orderResponseItemList(itemResponses)
//                .build();
//    }
//
//    private User getAuthenticatedUser() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return userRepository.findByUsernameOrThrow(username);
//    }
//
//    public OrderResponse getOrderById(Long id) {
//        Order order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
//        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
//                .map(item -> OrderItemResponse.builder()
//                        .id(item.getId())
//                        .productId(item.getProduct().getId())
//                        .productName(item.getProduct().getProductName())
//                        .quantity(item.getQuantity())
//                        .price(item.getPrice())
//                        .totalPrice(item.calculateTotalPrice())
//                        .thumbnailProduct(item.getProduct().getImageThumbnail())
//                        .build())
//                .collect(Collectors.toList());
//        return OrderResponse.builder()
//                .orderId(order.getId())
//                .imageOrderSuccess(order.getImageOrderSuccess())
//                .address(order.getAddress())
//                .paymentMethod(order.getPaymentMethod())
//                .orderDate(order.getOrderDate())
//                .totalAmount(order.getTotalAmount())
//                .paymentStatus(order.getPaymentStatus())
//                .orderResponseItemList(itemResponses)
//                .build();
//    }
//
//    private List<OrderItem> createOrderItemsFromCart(Cart cart, Order order) {
//        return cart.getItems().stream()
//                .map(cartItem -> {
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setOrder(order);
//                    orderItem.setProduct(cartItem.getProduct());
//                    orderItem.setQuantity(cartItem.getQuantity());
//                    orderItem.setPrice(cartItem.getPrice());
//                    orderItem.setTotalPrice(cartItem.getPrice() * cartItem.getQuantity());
//                    return orderItem;
//                })
//                .collect(Collectors.toList());
//    }
//
//    private Order buildOrder(Cart cart, Address address, EnumPaymentMethod paymentMethod) {
//        return Order.builder()
//                .totalAmount(cart.getTotalPrice())
//                .username(cart.getUser().getUsername())
//                .orderDate(LocalDateTime.now())
//                .paymentMethod(EnumPaymentMethod.COD)
//                .address(address)
//                .paymentStatus(EnumPayment.NOT_PAID)
//                .status(EnumProcess.PENDING)
//                .build();
//    }
//
////    public List<Order> getOrdersByCustomer(int page, int size) {
////        User user = getAuthenticatedUser();
////        List<Order> list = orderRepository.findAll()
////                .stream()
////                .filter(order -> orderRepository.findByUser(user))
////                .toList();
////
////
////
////        return OrderResponse;
////    }
//
//    private void clearCart(Cart cart) {
//        cartItemRepository.deleteAll(cart.getItems());
//        cart.getItems().clear();
//        cart.setTotalPrice(0.0);
//        cartRepository.save(cart);
//    }
//}
