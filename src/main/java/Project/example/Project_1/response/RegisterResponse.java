package Project.example.Project_1.response;

import Project.example.Project_1.valid.ValidEmail;
import Project.example.Project_1.valid.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    String userId;
    @NotNull(message = "Ngày sinh nhật không được null")
    private LocalDate birthday;
    @NotBlank(message = "SĐT không được trống")
    @ValidPhoneNumber
    private String phone;
    @NotBlank(message = "Email không được để trống")
    @ValidEmail
    private String email;
    @NotBlank(message = "Giới tính bắt buộc chọn")
    private String gender;
    @NotNull(message = "Name không được null")
    private String fullName;


}
