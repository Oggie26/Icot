package Project.example.Project_1.response;

import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
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
    private Date createdAt;
    private Date updatedAt;
}
