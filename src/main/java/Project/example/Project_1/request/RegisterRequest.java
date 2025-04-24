package Project.example.Project_1.request;

import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.valid.ValidEmail;
import Project.example.Project_1.valid.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull(message = "Username không dược null")
    private String username;
    @NotNull(message = "Password không dược null")
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String password ;
    @NotNull(message = "Ngày sinh nhật không được null")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    @NotBlank(message = "SĐT không được trống")
    @ValidPhoneNumber(message = "{phone.invalid}")
    private String phone;
    @NotBlank(message = "Email không được để trống")
    @ValidEmail(message = "{email.invalid}")
    private String email;
    @NotBlank(message = "Giới tính bắt buộc chọn")
    private String gender;
    @NotNull(message = "Name không được null")
    private String fullName;
//    @Enumerated(EnumType.STRING)
//    private EnumStatus status;
//    @Enumerated(EnumType.STRING)
//    private EnumRole role;

}
