package Project.example.Project_1.request;

import Project.example.Project_1.enums.DiscountType;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherUpdateRequest {
    String code;
    Integer discount;
    DiscountType discountType;
    Double minOrderValue;
    String description;
    Integer point;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
