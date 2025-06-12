package Project.example.Project_1.response;

import Project.example.Project_1.enity.*;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumSize;
import Project.example.Project_1.request.ImageCusRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    Date createdDate;
    EnumBookOrder enumBookOrder;
    TypePrint typePrint;
    List<ImageCus> imageSkins;
    String customerName;
    Address address;
    String designName;

}
