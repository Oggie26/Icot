package Project.example.Project_1.service;

import Project.example.Project_1.enity.Fabric;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.FabricRepository;
import Project.example.Project_1.request.FabricCreationRequest;
import Project.example.Project_1.request.FabricUpdateRequest;
import Project.example.Project_1.response.FabricResponse;
import Project.example.Project_1.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FabricService {

    @Autowired
    FabricRepository fabricRepository;

    @Transactional
    public FabricResponse createFabric (FabricCreationRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        boolean checkName = fabricRepository.findByFabricName(request.getFabricName()).isPresent();
        if(checkName){
            throw new AppException(ErrorCode.FABRIC_NAME_EXISTED);
        }
        Fabric fabric = Fabric.builder()
                .fabricName(request.getFabricName())
                .status(EnumStatus.ACTIVE)
                .price(request.getPrice())
                .build();
        fabric.setIsDeleted(false);
        fabricRepository.save(fabric);
        return FabricResponse.builder()
                .id(fabric.getId())
                .status(EnumStatus.ACTIVE)
                .fabricName(fabric.getFabricName())
                .price(fabric.getPrice())
                .price(request.getPrice())
                .build();
    }

    @Transactional
    public FabricResponse updateFabric (FabricUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND));
        boolean checkName = fabricRepository.findByFabricName(fabric.getFabricName()).isPresent();
        if (checkName) {
            throw new AppException(ErrorCode.FABRIC_NAME_EXISTED);
        }
        fabric.setFabricName(request.getFabricName());
        fabric.setPrice(request.getPrice());
        fabric.setIsDeleted(false);
        fabricRepository.save(fabric);

        return FabricResponse.builder()
                .id(fabric.getId())
                .status(EnumStatus.ACTIVE)
                .fabricName(fabric.getFabricName())
                .price(fabric.getPrice())
                .price(fabric.getPrice())
                .build();
    }

    public void deleteFabric (Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND));
        fabric.setIsDeleted(true);
        fabric.setStatus(EnumStatus.DELETED);
        fabricRepository.save(fabric);
    }

    public void changeStatus (Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND));
        if (fabric.getStatus().equals(EnumStatus.ACTIVE)) {
            fabric.setStatus(EnumStatus.INACTIVE);
        } else {
            fabric.setStatus(EnumStatus.ACTIVE);
        }
        fabricRepository.save(fabric);
    }

    public List<Fabric> getAllFabrics(){
        List<Fabric> fabrics = fabricRepository.findAll()
                .stream()
                .filter(fabric ->  fabric.getStatus().equals(EnumStatus.ACTIVE))
                .toList();
        return fabrics;
    }

    public FabricResponse getFabricById(Long id) {
        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND));
        return FabricResponse.builder()
                .id(fabric.getId())
                .fabricName(fabric.getFabricName())
                .price(fabric.getPrice())
                .status(fabric.getStatus())
                .build();
    }

    public PageResponse<FabricResponse> searchFabric(String key, int page, int size) {
        if (key == null || key.trim().isEmpty()) {
            key = "";
        }

        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 10;
        }

        // Tạo Pageable cho phân trang
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());

        // Tìm kiếm trong repository
        Page<Fabric> fabricPage = fabricRepository.findByFabricNameContainingIgnoreCase(key, pageable);

        // Chuyển đổi Fabric sang FabricResponse
        List<FabricResponse> fabricResponses = fabricPage.getContent().stream()
                .map(fabric -> FabricResponse.builder()
                        .id(fabric.getId())
                        .fabricName(fabric.getFabricName())
                        .price(fabric.getPrice())
                        .status(fabric.getStatus())
                        .build())
                .collect(Collectors.toList());

        // Tạo PageResponse
        return new PageResponse<>(
                fabricResponses,
                fabricPage.getNumber(),
                fabricPage.getTotalPages(),
                fabricPage.getTotalElements()
        );
    }
}
