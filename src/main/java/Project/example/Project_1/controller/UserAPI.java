package Project.example.Project_1.controller;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.request.UserRequest;
import Project.example.Project_1.response.GetUserResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.response.UserResponse;
import Project.example.Project_1.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class UserAPI {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<PageResponse<GetUserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws NotFoundException {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<GetUserResponse> getUserByUsername(@PathVariable String username) throws NotFoundException {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) throws NotFoundException {
        return ResponseEntity.ok(userService.addNewUser(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) throws NotFoundException {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<User> disableUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.disableUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
