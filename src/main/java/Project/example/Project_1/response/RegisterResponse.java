package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumRole;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterResponse {
    private String username;
    private String password ;
    private Date birthday;
    private String  phone;
    private String email;
    private String gender;
    private String fullName;
    private Boolean status;
    private EnumRole role;
    private String address;

}
