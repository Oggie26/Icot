package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumMaterial;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FabricCreationRequest {
    String fabricName;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    Double price;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
