package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    String token;
    String fullName;
    @Enumerated(EnumType.STRING)
    EnumRole role;
}
