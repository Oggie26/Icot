package Project.example.Project_1.response;

import Project.example.Project_1.enums.DiscountType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {
    Long id;
    String code;
    Integer discount;
    DiscountType discountType;
    Double minOrderValue;
    String description;
    Integer point;
    Integer quantity;
}
