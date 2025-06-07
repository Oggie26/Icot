package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumSize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SizeRequest {
    @Enumerated(EnumType.STRING)
    EnumSize size;
}
