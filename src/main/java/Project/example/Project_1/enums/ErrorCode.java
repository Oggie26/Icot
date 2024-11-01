package Project.example.Project_1.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    //10XX
    RESOURCE_NOT_FOUND(1000, "Resource not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1001, "Tên tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "Tài khoản không tồn tại", HttpStatus.BAD_REQUEST),
    ACCOUNT_INCORRECT(1003, " Tài khoản hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    ACCOUNT_BLOCKED(1004,"Tài khoản này đã bị chặn", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1005,"Email đã được sử dụng", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(1006, "Email send fail", HttpStatus.BAD_REQUEST),
    //11xx
    INVALID_KEY(1100, "Invalid uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_JSON(1101, "Json invalid", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1102, "Otp expired", HttpStatus.BAD_REQUEST),
    INVALID_OTP(1103, "Invalid Otp", HttpStatus.BAD_REQUEST),

    //12xx
    UNAUTHENTICATED(1201, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    ;


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

