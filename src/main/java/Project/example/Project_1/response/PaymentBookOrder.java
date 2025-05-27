package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumProcess;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentBookOrder {
    Double price;
    LocalDateTime dateTime;
    Long bookOrderId;
    @Enumerated(EnumType.STRING)
    EnumBookOrder status;
}
