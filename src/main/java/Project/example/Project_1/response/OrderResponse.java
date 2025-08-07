package Project.example.Project_1.response;

import Project.example.Project_1.enity.Address;
import Project.example.Project_1.enity.Payment;
import Project.example.Project_1.enums.EnumPayment;
import Project.example.Project_1.enums.EnumPaymentMethod;
import Project.example.Project_1.enums.EnumProcess;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    Long orderId;
    Double totalAmount;
    EnumProcess status;
    String username;
    LocalDateTime orderDate;
    EnumPaymentMethod paymentMethod;
    EnumPayment paymentStatus;
    Address address;
    List<OrderItemResponse> orderResponseItemList;
    String imageOrderSuccess;
}
