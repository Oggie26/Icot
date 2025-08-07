package Project.example.Project_1.service;

import Project.example.Project_1.enity.Otp;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.OtpRepository;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.util.OtpUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OptService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    PostmarkService postmarkService;

    @Transactional
    public Otp generateAndSaveOtp(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo OTP ngẫu nhiên
        String otp = OtpUtil.generateOTP();

        // Xác định thời gian hết hạn (ví dụ 10 phút sau khi tạo)
        Date expirationTime = new Date(System.currentTimeMillis() + (10 * 60 * 1000)); // 10 phút

        // Tạo và lưu OTP
        Otp otpRecord = Otp.builder()
                .user(user)
                .otp(otp)
                .expirationTime(expirationTime)
                .createdAt(new Date())
                .build();

        return otpRepository.save(otpRecord);
    }

    public boolean validateOtp(String userId, String otp) {
        Date currentTime = new Date();
        Optional<Otp> otpRecord = otpRepository.findByUserIdAndOtpAndExpirationTimeAfter(userId, otp, currentTime);

        return otpRecord.isPresent();
    }

    @Transactional
    public void resendOtp(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Vô hiệu hóa OTP cũ (nếu có)
        Optional<Otp> existingOtp = otpRepository.findByUser(user);
        existingOtp.ifPresent(otp -> {
            otp.setExpirationTime(new Date()); // Thay đổi thời gian hết hạn để đánh dấu OTP cũ là không hợp lệ
            otpRepository.save(otp); // Lưu lại OTP đã vô hiệu hóa
        });

        // Tạo OTP mới
        Otp newOtp = generateOtpForUser(user);

        // Gửi OTP mới qua email
        try {
            postmarkService.sendVerificationEmailWithOTP(user.getEmail(), user.getUsername(), newOtp.getOtp());
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }

        // Lưu OTP mới vào cơ sở dữ liệu
        otpRepository.save(newOtp);
    }

    @Transactional
    public void resendOtpByEmail(String email) {
        User user = userRepository.findUserByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Vô hiệu hóa OTP cũ (nếu có)
        Optional<Otp> existingOtp = otpRepository.findByUser(user);
        existingOtp.ifPresent(otp -> {
            otp.setExpirationTime(new Date()); // Thay đổi thời gian hết hạn để đánh dấu OTP cũ là không hợp lệ
            otpRepository.save(otp); // Lưu lại OTP đã vô hiệu hóa
        });

        // Tạo OTP mới
        Otp newOtp = generateOtpForUser(user);

        // Gửi OTP mới qua email
        try {
            postmarkService.sendVerificationEmailWithOTP(user.getEmail(), user.getUsername(), newOtp.getOtp());
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }

        // Lưu OTP mới vào cơ sở dữ liệu
        otpRepository.save(newOtp);
    }

    public void verifyOtp(String userId, String otpCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Kiểm tra OTP hợp lệ
        Optional<Otp> otpOptional = otpRepository.findByUserAndOtp(user, otpCode);
        if (otpOptional.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_OTP); // Nếu không tìm thấy OTP trùng khớp
        }

        Otp otp = otpOptional.get();

        // Kiểm tra thời gian hết hạn của OTP
        if (otp.getExpirationTime().before(new Date())) {
            throw new AppException(ErrorCode.OTP_EXPIRED); // OTP đã hết hạn
        }

        // Nếu OTP hợp lệ và chưa hết hạn, có thể tiến hành xác minh tài khoản
        user.setStatus(EnumStatus.ACTIVE); // Ví dụ: chuyển trạng thái tài khoản thành "ACTIVE"
        userRepository.save(user);

        // Xóa OTP đã xác thực khỏi cơ sở dữ liệu để không sử dụng lại
        otpRepository.delete(otp);
    }

    private Otp generateOtpForUser(User user) {
        // Tạo mã OTP ngẫu nhiên (hoặc dùng một phương thức sinh mã OTP phù hợp)
        String otpCode = OtpUtil.generateOTP();

        // Thiết lập thời gian hết hạn cho OTP (10 phút từ thời điểm tạo)
        Date expirationTime = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10 phút sau

        // Tạo đối tượng OTP mới
        Otp otp = new Otp();
        otp.setOtp(otpCode);
        otp.setUser(user);
        otp.setExpirationTime(expirationTime);
        otp.setCreatedAt(new Date()); // Thời gian tạo OTP mới

        return otp;
    }
}
