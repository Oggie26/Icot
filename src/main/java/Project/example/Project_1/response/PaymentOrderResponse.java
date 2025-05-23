package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumProcess;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrderResponse {
    Double price;
    LocalDateTime dateTime;
    Long orderId;
    @Enumerated(EnumType.STRING)
    EnumProcess status;
}

