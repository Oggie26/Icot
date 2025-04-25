package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserSearchRequest {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private EnumRole role;
    private EnumStatus status;
    private String gender;
    private LocalDate fromBirthday;
    private LocalDate toBirthday;
    private Date fromCreatedAt;
    private Date toCreatedAt;
    private Boolean isDeleted;
}

