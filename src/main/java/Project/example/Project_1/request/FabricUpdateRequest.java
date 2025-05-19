package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumMaterial;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FabricUpdateRequest {
    Long id;
    String fabricName;
    @NotBlank(message = "Price not null")
    @NotBlank(message = "Price not null")
    Double price;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
