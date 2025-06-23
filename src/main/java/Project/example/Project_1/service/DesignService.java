package Project.example.Project_1.service;

import Project.example.Project_1.enity.AbstractEntity;
import Project.example.Project_1.enity.Design;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.DesignRepository;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.DesignerRequest;
import Project.example.Project_1.response.DesignerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignService {

    @Autowired
    DesignRepository designRepository;

    @Autowired
    UserRepository userRepository;

    public DesignerResponse createDesigner(DesignerRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();

        User user  = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Design design = Design.builder()
                .designName(user.getFullName())
                .user(user)
                .description(request.getDescription())
                .fileName(request.getFileName())
                .fileUrl(request.getFileUrl())
                .status(EnumStatus.ACTIVE)
                .build();
        design.setIsDeleted(false);
        designRepository.save(design);

        return DesignerResponse.builder()
                .id(design.getId())
                .status(EnumStatus.ACTIVE)
                .designerName(design.getDesignName())
                .description(design.getDescription())
                .fileUrl(design.getFileUrl())
                .fileName(design.getFileName())
                .user(user)
                .build();
    }

    public DesignerResponse updateDesigner(DesignerRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();

        User user  = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Design design = designRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        design.setDescription(request.getDescription());
        design.setFileName(request.getFileName());
        design.setFileUrl(request.getFileUrl());
        design.setDesignName(user.getFullName());
        design.setIsDeleted(false);
        designRepository.save(design);

        return DesignerResponse.builder()
                .id(design.getId())
                .status(EnumStatus.ACTIVE)
                .designerName(design.getDesignName())
                .description(design.getDescription())
                .fileUrl(design.getFileUrl())
                .fileName(design.getFileName())
                .user(design.getUser())
                .build();
    }

    public void deleteDesigner(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Design design = designRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));
        design.setIsDeleted(true);
        designRepository.save(design);
    }

    public List<Design> getAllDesigners(){
        List<Design> list= designRepository.findAll()
                .stream()
                .toList();
        return list;
    }

    public Design getDesigner(Long id){
        Design design = designRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));
        return design;
    }
}
