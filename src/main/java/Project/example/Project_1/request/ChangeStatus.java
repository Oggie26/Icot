package Project.example.Project_1.request;
import Project.example.Project_1.enity.ImageDesign;
import Project.example.Project_1.enums.EnumBookOrder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeStatus {
    Long designId;
    String designName;
    @NotNull
    String response;
    String imageDelivery;
    List<ImageDesignList> imageDesign;

}

