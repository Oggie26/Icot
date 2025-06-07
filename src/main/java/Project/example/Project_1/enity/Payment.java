package Project.example.Project_1.enity;

import Project.example.Project_1.enums.EnumPayment;
import Project.example.Project_1.enums.EnumPaymentMethod;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    EnumPaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    EnumPayment payment;

    @Column
    Double amount;
}
