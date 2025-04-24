package Project.example.Project_1.controller;

import Project.example.Project_1.request.UserCreateRequest;
import Project.example.Project_1.request.UserUpdateRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.UserStaffResponse;
import Project.example.Project_1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class UserController {

    UserService userService;

    @PostMapping
    @Operation(summary = "Tạo người dùng mới")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserStaffResponse> createUser(@RequestBody UserCreateRequest request) {
        return ApiResponse.<UserStaffResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo người dùng thành công")
                .result(userService.createUser(request))
                .build();
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Cập nhật thông tin người dùng")
    public ApiResponse<UserStaffResponse> updateUser(@PathVariable String userId,
                                                     @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserStaffResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật người dùng thành công")
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Xoá người dùng (soft delete)")
    public ApiResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xoá người dùng thành công")
                .build();
    }

    @PutMapping("/disable/{userId}")
    @Operation(summary = "Vô hiệu hoá người dùng")
    public ApiResponse<Void> disableUser(@PathVariable String userId) {
        userService.disableUser(userId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Vô hiệu hoá người dùng thành công")
                .build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Xem chi tiết người dùng")
    public ApiResponse<UserStaffResponse> getUser(@PathVariable String userId) {
        return ApiResponse.<UserStaffResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin người dùng thành công")
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách người dùng (status = INACTIVE)")
    public ApiResponse<List<UserStaffResponse>> getUsers() {
        return ApiResponse.<List<UserStaffResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách người dùng thành công")
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/profile")
    @Operation(summary = "Xem profile người dùng đang đăng nhập")
    public ApiResponse<UserStaffResponse> getProfile() {
        return ApiResponse.<UserStaffResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin profile thành công")
                .result(userService.getProfile())
                .build();
    }
}
