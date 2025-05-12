package Project.example.Project_1.controller;

import Project.example.Project_1.request.LoginRequest;
import Project.example.Project_1.request.RegisterRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.LoginResponse;
import Project.example.Project_1.response.RegisterResponse;
import Project.example.Project_1.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@Validated
@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
@Tag(name = "Authentication Controller")

public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("login")
    @Operation(summary = "Đăng nhập", description = "API đăng nhập")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Đăng nhập thành công")
                .result(authenticationService.login(loginRequest))
                .build();
    }

    @PostMapping("register")
    @Operation(summary = "Đăng kí", description = "API đăng kí")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
        return ApiResponse.<RegisterResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Đăng kí thành công")
                .result(authenticationService.register(registerRequest))
                .build();
    }


}
