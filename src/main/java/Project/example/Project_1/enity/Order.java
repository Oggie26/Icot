package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumPayment;
import Project.example.Project_1.enums.EnumPaymentMethod;
import Project.example.Project_1.enums.EnumProcess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends  AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "total_amount")
    Double totalAmount;

    @Column(name = "username")
    String username;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "updated_at")
    Date updatedAt;

    @Column(name = "updated_by")
    String updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 50)
    EnumProcess status;

    @Enumerated(EnumType.STRING)
    EnumPayment paymentStatus;

    @Enumerated(EnumType.STRING)
    EnumPaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    List<OrderItem> orderItems;

    @Column(name = "image_order_success")
    String imageOrderSuccess;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<ProcessOrder> processOrders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    Address address;
}


