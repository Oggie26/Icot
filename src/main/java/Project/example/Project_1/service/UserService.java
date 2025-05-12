package Project.example.Project_1.service;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.UserCreateRequest;
import Project.example.Project_1.request.UserUpdateRequest;
import Project.example.Project_1.response.AddressResponse;
import Project.example.Project_1.response.UserStaffResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
     UserRepository userRepository;
    @Autowired
     PasswordEncoder passwordEncoder;

    @Transactional
    public UserStaffResponse createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.findUserByEmailAndIsDeletedFalse(userCreateRequest.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (userRepository.findUserByPhoneAndIsDeletedFalse(userCreateRequest.getPhone()).isPresent()) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        User user = User.builder()
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .avatar(userCreateRequest.getAvatar())
                .point(0)
                .phone(userCreateRequest.getPhone())
                .gender(userCreateRequest.getGender())
                .status(EnumStatus.INACTIVE)
                .birthday(userCreateRequest.getBirthday())
                .addresses(null)
                .role(userCreateRequest.getRole())
                .phone(userCreateRequest.getPhone())
                .email(userCreateRequest.getEmail())
                .fullName(userCreateRequest.getFullName())
                .build();

        userRepository.save(user);

        return UserStaffResponse.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .point(user.getPoint())
                .gender(user.getGender())
                .status(EnumStatus.INACTIVE)
                .birthday(user.getBirthday())
                .address(null)
                .build();
    }

    @Transactional
    public UserStaffResponse updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userRepository.findUserByEmailAndIsDeletedFalse(userUpdateRequest.getEmail())
                .filter(existingUser -> !existingUser.getId().equals(userId))
                .ifPresent(existingUser -> {
                    throw new AppException(ErrorCode.EMAIL_EXISTED);
                });

        userRepository.findUserByPhoneAndIsDeletedFalse(userUpdateRequest.getPhone())
                .filter(existingUser -> !existingUser.getId().equals(userId))
                .ifPresent(existingUser -> {
                    throw new AppException(ErrorCode.PHONE_EXISTED);
                });

        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setAvatar(userUpdateRequest.getAvatar());
        user.setPhone(userUpdateRequest.getPhone());
        user.setGender(userUpdateRequest.getGender());
        user.setBirthday(userUpdateRequest.getBirthday());
        user.setRole(userUpdateRequest.getRole());
        user.setStatus(user.getStatus() != null ? user.getStatus() : EnumStatus.INACTIVE);
        userRepository.save(user);

        return UserStaffResponse.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .point(user.getPoint())
                .gender(user.getGender())
                .status(user.getStatus())
                .birthday(user.getBirthday())
                .address(null)
                .build();
    }

    public void deleteUser(String userId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.save(user);
    }

    public void disableUser (String  userId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(EnumStatus.INACTIVE);
        userRepository.save(user);
    }

    public UserStaffResponse getUser(String userId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return UserStaffResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .point(user.getPoint())
                .gender(user.getGender())
                .status(user.getStatus())
                .birthday(user.getBirthday())
                .role(user.getRole())
                .address(user.getAddresses() == null ? null :
                        user.getAddresses().stream()
                                .map(address -> AddressResponse.builder()
                                        .id(address.getId())
                                        .street(address.getStreet())
                                        .city(address.getCity())
                                        .isDefault(address.getIsDefault())
                                        .build())
                                .toList().toString())
                .build();
    }

    public List<UserStaffResponse> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserStaffResponse.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .avatar(user.getAvatar())
                        .point(user.getPoint())
                        .gender(user.getGender())
                        .status(user.getStatus())
                        .birthday(user.getBirthday())
                        .role(user.getRole())
                        .address(user.getAddresses() == null ? null :
                                user.getAddresses().stream()
                                        .map(address -> AddressResponse.builder()
                                                .id(address.getId())
                                                .street(address.getStreet())
                                                .city(address.getCity())
                                                .isDefault(address.getIsDefault())
                                                .build())
                                        .toList().toString())
                        .build())
                .filter(user -> user.getStatus().equals(EnumStatus.ACTIVE))
                .toList();
    }

    public UserStaffResponse getProfile() {
        // Lấy thông tin người dùng từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Trả về response
        return UserStaffResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .point(user.getPoint())
                .gender(user.getGender())
                .status(user.getStatus())
                .birthday(user.getBirthday())
                .role(user.getRole())
                .address(null) // hoặc ánh xạ nếu có
                .build();
    }



}
