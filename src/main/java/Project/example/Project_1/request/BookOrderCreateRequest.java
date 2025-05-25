package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumSize;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookOrderCreateRequest {
    @Enumerated(EnumType.STRING)
    EnumSize size;
    Integer quantity;
    String description;
    String color;
    Double totalPrice;
    Long categoryId;
    Long fabricId;
    Long TypePrint;


}
