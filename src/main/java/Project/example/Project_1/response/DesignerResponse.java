package Project.example.Project_1.response;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignerResponse {
    Long id;
    String designerName;
    String description;
    String fileName;
    String fileUrl;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
    User user;

}
