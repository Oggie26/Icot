package Project.example.Project_1.service;


import Project.example.Project_1.enity.User;
import Project.example.Project_1.exception.AuthException;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.LoginRequest;
import Project.example.Project_1.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public User getCurrentAccount() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getId()) != null ? userRepository.findById(user.getId()) : null;
    }

    //Login
    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findUserByUsername(loginRequest.getUsername());

        if (user == null) {
            throw new AuthException("User not found");
        }

        if (loginRequest.getPassword() == null) {
            throw new AuthException("Password cannot be null");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new AuthException("Invalid credentials");
        }

        if (!user.getStatus()) {
            throw new AuthException("Account blocked!!!");
        }
        UserResponse userResponse = new UserResponse();
        String token = tokenService.generateToken(user);
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setGender(user.getGender());
        userResponse.setBirthday(user.getBirthday());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        userResponse.setToken(token);
        return userResponse;
    }
}
