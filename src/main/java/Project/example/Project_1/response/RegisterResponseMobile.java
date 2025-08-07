package Project.example.Project_1.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseMobile {
    String userId;
    String username;
    String password;
    String email;
    String phone;
    String fullName;
}
