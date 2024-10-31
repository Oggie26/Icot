package Project.example.Project_1.service;


import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.exception.AuthException;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.EmailDetail;
import Project.example.Project_1.request.LoginRequest;
import Project.example.Project_1.request.RegisterRequest;
import Project.example.Project_1.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public User getCurrentAccount() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserById(user.getId()) != null ? userRepository.findUserById(user.getId()) : null;
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
        userResponse.setUser(user);
        userResponse.setToken(token);
        return userResponse;
    }

    //Register
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        UserResponse userResponse = new UserResponse();
        User user = new User();
        try {
            user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setFullName(registerRequest.getFullName());
            user.setPassword(registerRequest.getPassword());
            user.setEmail(registerRequest.getEmail());
            user.setAddress(registerRequest.getAddress());
            user.setBirthday(registerRequest.getBirthday());
            user.setGender(registerRequest.getGender());
            user.setPhone(registerRequest.getPhone());
            user.setRole(EnumRole.CUSTOMER);
            user.setStatus(true);
            userResponse.setUser(user);
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(user.getEmail());
            emailDetail.setMsgBody("Welcome to join The Happy Food");
            emailDetail.setSubject("TheHappyFood");
            emailDetail.setButton("Login To System");
            emailDetail.setLink("localhost:8080/login");
            emailDetail.setFullName(user.getFullName());
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    emailService.sendMailTemplate(emailDetail);
                }
            };
            new Thread(r).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        userRepository.save(user);
        return userResponse;
    }


}
