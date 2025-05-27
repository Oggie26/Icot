package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypePrintResponse {
    Long id;
    Double price;
    String name;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
