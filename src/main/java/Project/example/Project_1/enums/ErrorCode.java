package Project.example.Project_1.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR(500, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    //10XX
    RESOURCE_NOT_FOUND(1000, "Resource not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1001, "Tên tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "Tài khoản không tồn tại", HttpStatus.BAD_REQUEST),
    ACCOUNT_INCORRECT(1003, " Tài khoản hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    ACCOUNT_BLOCKED(1004,"Tài khoản này đã bị chặn", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1005,"Email đã được sử dụng", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(1006, "Email send fail", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(1007, "Sản phẩm không tồn tại", HttpStatus.BAD_REQUEST),
    PRODUCT_INACTIVE(1008, "Sản phẩm này đẫ không còn hoặc đã xoá ", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(1009, "Address not found", HttpStatus.BAD_REQUEST),
    TAG_NOT_FOUND(1010, "Tag not found", HttpStatus.BAD_REQUEST),
    TAG_NAME_EXISTED(1011, "Tag name existed", HttpStatus.BAD_REQUEST),
    TAG_NAME_NOT_FOUND(1012, "Tag name not found", HttpStatus.BAD_REQUEST),
    TAG_NAME_ALREADY_EXISTS(1013, "Tag name already exists", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(1014, "Phone number existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1015, "User not found", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_FOUND(1016, "Voucher not found", HttpStatus.BAD_REQUEST),
    VOUCHER_CODE_ALREADY_EXISTS(1017, "Voucher code already exists", HttpStatus.BAD_REQUEST),
    FABRIC_NAME_EXISTED(1018, "Fabric name existed", HttpStatus.BAD_REQUEST),
    FABRIC_NOT_FOUND(1019, "Fabric not found", HttpStatus.BAD_REQUEST),
    BLOG_NAME_EXISTED(1020, "Blog name existed", HttpStatus.BAD_REQUEST),
    BLOG_NOT_EXIST(1021, "Blog not existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1022, "Category not found", HttpStatus.BAD_REQUEST),
    CATEGORY_HAS_PRODUCTS(1023, "Category has products", HttpStatus.BAD_REQUEST),

    //11xx
    INVALID_KEY(1100, "Invalid uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_JSON(1101, "Json invalid", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1102, "Otp expired", HttpStatus.BAD_REQUEST),
    INVALID_OTP(1103, "Invalid Otp", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(1104, "Quantity invalid", HttpStatus.BAD_REQUEST),
    INVALID_LOGIN(1105, "Invalid Login", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1106, "Invalid Password", HttpStatus.BAD_REQUEST),
    INVALID_PHONE(1107, "Invalid Phone", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1108, "Invalid Email", HttpStatus.BAD_REQUEST),
    INVALID_EXCHANGE_VOUCHER(1109, "Invalid Exchange voucher", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_NAME(1110, "Invalid Product name", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_PRICE(1111, "Invalid Product price! Must be a positive number", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_DESCRIPTION(1112, "Invalid Product description", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_SIZE(1113, "Invalid Product size", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_IMAGE(1114, "Invalid Product image", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_CATEGORY(1115, "Invalid Product category", HttpStatus.BAD_REQUEST),
    INVALID_BLOG_NAME(1116, "Invalid Blog name", HttpStatus.BAD_REQUEST),
    ERROR_SAVE_PRODUCT(1117, "Error when saving product", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_NAME(1118, "Invalid Category name", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_DESCRIPTION(1119, "Invalid Category description", HttpStatus.BAD_REQUEST),
    ERROR_SAVE_CATEGORY(1120, "Error when saving category", HttpStatus.BAD_REQUEST),
    ERROR_UPDATE_PRODUCT(1121, "Error when updating product", HttpStatus.BAD_REQUEST),

    //12xx
    UNAUTHENTICATED(1201, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NOT_ENOUGH_POINT(1202, "Not enough point", HttpStatus.BAD_REQUEST),
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

