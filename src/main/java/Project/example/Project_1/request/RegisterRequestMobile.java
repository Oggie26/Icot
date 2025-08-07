package Project.example.Project_1.request;

import lombok.Data;

@Data
public class RegisterRequestMobile {
    String username;
    String password;
    String email;
    String phone;
    String fullName;
}
