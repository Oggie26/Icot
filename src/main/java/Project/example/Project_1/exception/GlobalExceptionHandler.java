package Project.example.Project_1.exception;

import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    /**
     * Xử lý lỗi validation khi dữ liệu request body không hợp lệ.
     * Ex: username không hợp lệ => username không đủ ký tự
     *     gender không hợp lệ => khác kiểu enum
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        ErrorCode errorCode = ErrorCode.INVALID_KEY; // default error code
        Map<String, Object> attributes = null;

        if (!((java.util.List<?>) errors).isEmpty()) {
            String enumKey = errors.get(0).getDefaultMessage();
            try {
                errorCode = ErrorCode.valueOf(enumKey); // convert to enum
                ConstraintViolation<?> constraintViolation = errors.get(0)
                        .unwrap(ConstraintViolation.class);
                attributes = constraintViolation.getConstraintDescriptor().getAttributes();
                log.info("Validation attributes: {}", attributes);
            } catch (IllegalArgumentException | ClassCastException e) {
                log.warn("Could not map validation error message to enum key: {}", e.getMessage());
            }
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage()
        );

        return ResponseEntity.badRequest().body(apiResponse);
    }

    /**
     * Xử lý lỗi json không hợp lệ
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        // Tạo đối tượng lỗi mặc định
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.INVALID_JSON.getCode());

        // Cung cấp thông điệp lỗi chi tiết
        String errorMessage = "JSON data invalid";

        // Log lỗi để kiểm tra chi tiết
        log.error("Error processing JSON: " + exception.getMessage());

        // Nếu lỗi JSON liên quan đến cấu trúc không hợp lệ hoặc định dạng, thông báo thêm
        if (exception.getMessage().contains("JSON parse error")) {
            errorMessage = "JSON data invalid. Please check again";
        }

        // Trả về thông điệp lỗi
        apiResponse.setMessage(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    /**
     * Xử lý lỗi validation trên Query Param hoặc Path Variable.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

        log.warn("Constraint validation failed: {}", errors);
        return ResponseEntity.badRequest()
                .body(ApiResponse.<Map<String, String>>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Validation failed")
                        .result(errors)
                        .build());
    }

    /**
     * Xử lý lỗi không có quyền truy cập (401 Unauthorized).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException exception) {
        log.warn("Access Denied: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message(ErrorCode.UNAUTHENTICATED.getMessage())
                        .build());
    }


    /**
     * Xử lý lỗi nhập data khong hop le
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.warn("Data violation: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Void>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Data input invalid")
                        .build());
    }

    /**
     * Xử lý lỗi có định nghĩa trong hệ thống (AppException).
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException exception) {
        log.error("Application error: {}", exception.getMessage());
        return ResponseEntity.status(exception.getErrorCode().getStatusCode())
                .body(ApiResponse.<Void>builder()
                        .code(exception.getErrorCode().getCode())
                        .message(exception.getMessage())
                        .build());

    }

    /**
     * Xử lý tất cả các lỗi chưa được bắt trước đó (Exception.class).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUncaughtException(Exception exception) {
        log.error("Uncaught exception: ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.<Void>builder()
                        .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                        .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }

}
