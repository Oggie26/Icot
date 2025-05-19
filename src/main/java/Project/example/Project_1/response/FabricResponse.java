package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumMaterial;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FabricResponse {
    Long id;
    String fabriceName;
    @NotBlank(message = "Price not null")
    Double price;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
