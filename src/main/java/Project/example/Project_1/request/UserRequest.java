package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserRequest {
    private String username;
    private String password ;
    private LocalDate birthday;
    private String  phone;
    private String email;
    private String gender;
    private String fullName;
    private EnumStatus status;
    private String address;
    private EnumRole role;
    private int point;
}
