package Project.example.Project_1.controller;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.UserCreateRequest;
import Project.example.Project_1.request.UserSearchRequest;
import Project.example.Project_1.request.UserUpdateRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.GetUserResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.response.UserStaffResponse;
import Project.example.Project_1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

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

    @GetMapping("/designers")
    @Operation(summary = "All Designer")
    public ApiResponse<List<User>> getUser() {
        List<User> list = userRepository.findAll()
                .stream()
                .filter(role -> role.getRole().equals(EnumRole.DESIGNER))
                .toList();
        return ApiResponse.<List<User>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sach designer ")
                .result(list)
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

    @GetMapping("/all")
    @Operation(summary = "Lấy danh sách người dùng (phân trang)")
    public ApiResponse<PageResponse<GetUserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<GetUserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách người dùng thành công")
                .result(userService.getAllUsers(page, size))
                .build();
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Lấy người dùng theo ID")
    public ApiResponse<User> getUserById(@PathVariable String id) {
        return ApiResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy người dùng thành công")
                .result(userService.getUserById(id))
                .build();
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Lấy người dùng theo tên đăng nhập")
    public ApiResponse<GetUserResponse> getUserByUsername(@PathVariable String username) {
        return ApiResponse.<GetUserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy người dùng thành công")
                .result(userService.getUserByUsername(username))
                .build();
    }

    @PostMapping("/search")
    @Operation(summary = "Tìm kiếm người dùng theo nhiều tiêu chí")
    public ApiResponse<PageResponse<GetUserResponse>> searchUsers(
            @RequestBody UserSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<GetUserResponse> users = userService.searchUsers(request, page, size);

        return ApiResponse.<PageResponse<GetUserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Tìm kiếm thành công")
                .result(users)
                .build();
    }
}
