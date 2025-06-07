package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStaffResponse {
    String id;
    String avatar;
    String fullName;
    String email;
    String username;
    LocalDate birthday;
    String phone;
    Integer point;
    String address;
    String gender;
    @Enumerated(EnumType.STRING)
    EnumRole role;
    @Enumerated(EnumType.STRING)
    EnumStatus status;
}
