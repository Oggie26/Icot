package Project.example.Project_1.service;

import Project.example.Project_1.enity.User;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.UserRequest;
import Project.example.Project_1.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User disableUser(long id){
        User user = userRepository.findUserById(id);
        user.setStatus(!user.getStatus());
        return userRepository.save(user);
    }

    @Transactional
    public UserResponse addNewUser(UserRequest userRequest){
        User user = new User();
        UserResponse userResponse = new UserResponse();
        user.setUsername(userRequest.getUsername());
        user.setFullName(userRequest.getFullName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setBirthday(userRequest.getBirthday());
        user.setGender(userRequest.getGender());
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());
        user.setPoint(0);
        user.setStatus(true);
        userResponse.setUser(user);
        userRepository.save(user);
        return userResponse;
    }

    @Transactional
    public UserResponse updateUser(long id , UserRequest userRequest){
        User user = userRepository.findUserById(id);
        user.setUsername(userRequest.getUsername());
        user.setFullName(userRequest.getFullName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setBirthday(userRequest.getBirthday());
        user.setGender(userRequest.getGender());
        user.setPhone(userRequest.getPhone());
        user.setRole(userRequest.getRole());
        user.setStatus(userRequest.getStatus());
        user.setPoint(userRequest.getPoint());
        UserResponse userResponse = new UserResponse();
        userRepository.save(user);
        userResponse.setUser(user);

        return userResponse;
    }
}
