package Project.example.Project_1.service;

import Project.example.Project_1.enity.Order;
import Project.example.Project_1.enity.Payment;
import Project.example.Project_1.enums.*;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.OrderRepository;
import Project.example.Project_1.repository.PaymentRepository;
import Project.example.Project_1.response.PaymentOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PayOsService payOsService;

    public PaymentOrderResponse paymentCode (Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND, "Thiếu ID của Design"));

        order.setPaymentMethod(EnumPaymentMethod.COD);
        order.setStatus(EnumProcess.DELIVERED);
        Payment payment = new Payment();
        payment.setPaymentMethod(EnumPaymentMethod.COD);
        payment.setAmount(order.getTotalAmount());
        paymentRepository.save(payment);
        return PaymentOrderResponse.builder()
                .price(order.getTotalAmount())
                .dateTime(LocalDateTime.now())
                .orderId(order.getId())
                .status(order.getStatus())
                .build();
    }


}
