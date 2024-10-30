package Project.example.Project_1.api;

import Project.example.Project_1.request.LoginRequest;
import Project.example.Project_1.response.UserResponse;
import Project.example.Project_1.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest){
        UserResponse loginResponse =  authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
