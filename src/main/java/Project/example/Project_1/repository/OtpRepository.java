package Project.example.Project_1.repository;

import Project.example.Project_1.enity.Otp;
import Project.example.Project_1.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    // Tìm OTP của người dùng còn hiệu lực
    Optional<Otp> findByUserIdAndOtpAndExpirationTimeAfter(String userId, String otp, Date currentTime);

    // Xóa OTP cũ đã hết hạn
    void deleteByExpirationTimeBefore(Date currentTime);
    Optional<Otp> findByUser(User user);

    // Tìm OTP dựa trên user và mã OTP (trong trường hợp xác thực OTP)
    Optional<Otp> findByUserAndOtp(User user, String otp);
}
