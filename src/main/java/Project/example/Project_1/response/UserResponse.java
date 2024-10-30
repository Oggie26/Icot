package Project.example.Project_1.response;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumRole;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse  {
    String token;
    Long id;
    String email;
    String fullName;
    String phone;
    String gender;
    EnumRole role;
    Boolean status;
    Date birthday;
}
