package Project.example.Project_1.request;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumProcess;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DesignerRequest {
    Long id;
    String description;
    String fileName;
    String fileUrl;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
