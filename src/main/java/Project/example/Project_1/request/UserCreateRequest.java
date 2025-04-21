package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UserCreateRequest {
    String avatar;
    String fullName;
    String email;
    String username;
    String password;
    String phone;
    LocalDate birthday;
    Integer point;
    String address;
    String gender;
    @Enumerated(EnumType.STRING)
    EnumRole role;
    @Enumerated(EnumType.STRING)
    EnumStatus status;

}
