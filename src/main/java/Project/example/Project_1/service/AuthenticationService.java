package Project.example.Project_1.service;


import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.LoginRequest;
import Project.example.Project_1.request.RegisterRequest;
import Project.example.Project_1.response.LoginResponse;
import Project.example.Project_1.response.RegisterResponse;
import Project.example.Project_1.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> getCurrentAccount() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.findUserByIdAndIsDeletedFalse(String.valueOf(user.getId())).isPresent() ?
//                userRepository.findUserByIdAndIsDeletedFalse(String.valueOf(user.getId())) : null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            User user = (User) authentication.getPrincipal();
            return userRepository.findUserByIdAndIsDeletedFalse(String.valueOf(user.getId()));
    }

    //Login
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findUserByIdAndIsDeletedFalse(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(user == null){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (user.getStatus().equals(EnumStatus.BLOCKED))
            throw new AppException(ErrorCode.ACCOUNT_BLOCKED);
        String token = tokenService.generateToken(user);
        // Trả về Token
        return LoginResponse.builder()
                .token(token)
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    @Transactional()
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = User.builder()
               .username(request.getUsername())
               .password(request.getPassword())
               .email(request.getEmail())
               .fullName(request.getFullName())
               .role(EnumRole.CUSTOMER)
               .birthday(request.getBirthday())
               .gender(request.getGender())
               .phone(request.getPhone())
               .point(0)
               .avatar("")
               .status(EnumStatus.ACTIVE)
               .build();
        user.setIsDeleted(false);
        userRepository.save(user);

        return RegisterResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .build();
    }
}
