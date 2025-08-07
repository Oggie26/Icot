package Project.example.Project_1.response;


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
public class CategoryResponse {
    Long id;
    String categoryName;
    String description ;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
