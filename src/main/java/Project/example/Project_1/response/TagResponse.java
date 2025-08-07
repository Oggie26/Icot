package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TagResponse    {
    String name;
    @Enumerated
    EnumStatus status;
    String description;
}
