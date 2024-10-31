package Project.example.Project_1.api;


import Project.example.Project_1.enity.User;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.UserRequest;
import Project.example.Project_1.response.UserResponse;
import Project.example.Project_1.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class UserAPI {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
         List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("addNewUser")
    public  ResponseEntity<UserResponse> addNewUser( @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.addNewUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("disableUser/{id}")
    public ResponseEntity<User> disableUser(@PathVariable long id){
        User user = userService.disableUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("updateUser/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id , @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.updateUser(id,userRequest);
        return ResponseEntity.ok(userResponse);
    }


}
