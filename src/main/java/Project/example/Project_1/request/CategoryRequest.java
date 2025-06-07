package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    Long id;
    String categoryName;
    String description ;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
