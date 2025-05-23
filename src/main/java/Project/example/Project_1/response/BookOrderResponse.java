package Project.example.Project_1.response;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.enity.Fabric;
import Project.example.Project_1.enity.TypePrint;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumSize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderResponse {
    Long id;
    @Enumerated(EnumType.STRING)
    EnumSize size;
    Integer quantity;
    String description;
    String color;
    Double totalPrice;
    Category category;
    Fabric fabric;
    User user;
    TypePrint typePrint;
}
