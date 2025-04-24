package Project.example.Project_1.service;

import Project.example.Project_1.config.PageMapper;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumRole;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.UserRequest;
import Project.example.Project_1.response.GetUserResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import javassist.NotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    PageMapper pageMaper;

    public User disableUser(String id){
        User user = userRepository.findUserById(id);
        user.setStatus(EnumStatus.BLOCKED);
        return userRepository.save(user);
    }

    public User deleteUser(String id){
        User user = userRepository.findUserById(id);
        user.setStatus(EnumStatus.BLOCKED);
        return userRepository.save(user);
    }

    private boolean isAdmin() throws NotFoundException {
        Optional<User> currentUser = authenticationService.getCurrentAccount();
        if (currentUser.isEmpty()) throw new NotFoundException("User is not found");
        return currentUser.get().getRole().equals(EnumRole.ADMIN);
    }

    @Transactional
    public UserResponse addNewUser(UserRequest userRequest) throws NotFoundException {
        User user = new User();
        modelMapper.map(userRequest, user);

        /*boolean isAdmin = authenticationService.getCurrentAccount()
                .map(User::getRole)
                .map(role -> role == EnumRole.ADMIN)
                .orElse(false);*/
        if(isAdmin())
            user.setRole(userRequest.getRole());
        else
            user.setRole(EnumRole.CUSTOMER);
        user.setPoint(0);
        user.setStatus(EnumStatus.ACTIVE);
        try{
            userRepository.save(user);
            return modelMapper.map(user, UserResponse.class);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new EntityNotFoundException("Error when saving a new user to db");
        }
    }

    @Transactional
    public UserResponse updateUser(String id, UserRequest userRequest) throws NotFoundException {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found with id: " + id);
        }

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(userRequest, user);

        if (isAdmin() && userRequest.getRole() != null) {
            user.setRole(userRequest.getRole());
        }

        if (userRequest.getStatus() != null) {
            user.setStatus(userRequest.getStatus());
        }

        if (userRequest.getPoint() >= 0) {
            user.setPoint(userRequest.getPoint());
        }

        try {
            userRepository.save(user);
            return modelMapper.map(user, UserResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Error when saving updated user to db");
        }
    }


    public User getUserById(String id){
        User user = userRepository.findUserById(id);
        return modelMapper.map(user, User.class);
    }

    public GetUserResponse getUserByUsername(String username) throws NotFoundException {
        boolean checkAdmin = isAdmin();
        if(checkAdmin){
            Optional<User> user = userRepository.findUserByUsername(username);
            if (user.isEmpty()) throw new NotFoundException("User is not found");
            return modelMapper.map(user, GetUserResponse.class);
        }
        else{
            Optional<User> currentUser = authenticationService.getCurrentAccount();
            if (currentUser.isEmpty()) throw new NotFoundException("User is not found");
            return modelMapper.map(currentUser, GetUserResponse.class);
        }
    }

//    public List<User> getAllUsers() throws NotFoundException {
//        Optional<User> currentUserId = authenticationService.getCurrentAccount();
//        if (currentUserId.isEmpty()) throw new NotFoundException("User is not found");
//        if (!currentUserId.get().getRole().equals(EnumRole.ADMIN)) throw new NotFoundException("User is not found");
//        List<User> users = userRepository.findAll();
//        return users.stream().map(user -> modelMapper.map(user, User.class)).toList();
//    }

    public PageResponse<GetUserResponse> getAllUsers(int page, int size) throws NotFoundException {
        boolean checkAdmin = isAdmin();
        if(!checkAdmin){
            throw new NotFoundException("You are not allowed to use this feature!");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> users = userRepository.findAll(pageable);

        return pageMaper.convertToPageResponse(users, user -> modelMapper.map(user, GetUserResponse.class));
    }


}
