package Project.example.Project_1.request;
import Project.example.Project_1.enums.EnumBookOrder;
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
public class ChangeStatus {
    @NotNull
    Long bookId;
    Long designId;
    String designName;
    String response;
}

