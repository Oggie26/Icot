package Project.example.Project_1.service;

import Project.example.Project_1.enity.TypePrint;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.TypePrintRepository;
import Project.example.Project_1.request.TypePrintRequest;
import Project.example.Project_1.response.TypePrintResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypePrintService {

    @Autowired
    TypePrintRepository typePrintRepository;

    @Transactional
    public TypePrintResponse createTypePrint(TypePrintRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(typePrintRepository.findByPrintNameAndIsDeletedFalse(request.getName()).isPresent()){
            throw new AppException((ErrorCode.TYPEPRINT_EXISTED));
        }

        TypePrint typePrint = new TypePrint();
        typePrint.setPrintName(request.getName());
        typePrint.setPrice(request.getPrice());
        typePrint.setIsDeleted(false);
        typePrint.setStatus(EnumStatus.ACTIVE);
        typePrintRepository.save(typePrint);
        return TypePrintResponse.builder()
                .id(typePrint.getId())
                .name(typePrint.getPrintName())
                .price(typePrint.getPrice())
                .status(typePrint.getStatus())
                .build();
    }

    @Transactional
    public TypePrintResponse updateTypePrint(TypePrintRequest request, Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));

        if(typePrintRepository.findByPrintNameAndIsDeletedFalse(request.getName()).isPresent()){
            throw new AppException((ErrorCode.TYPEPRINT_EXISTED));
        }

        typePrint.setPrintName(request.getName());
        typePrint.setPrice(request.getPrice());
        typePrint.setIsDeleted(false);
        typePrintRepository.save(typePrint);
        return TypePrintResponse.builder()
                .id(typePrint.getId())
                .name(typePrint.getPrintName())
                .price(typePrint.getPrice())
                .status(typePrint.getStatus())
                .build();
    }

    public TypePrintResponse getTypePrintById(Long id){

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));

        return TypePrintResponse.builder()
                .id(typePrint.getId())
                .name(typePrint.getPrintName())
                .price(typePrint.getPrice())
                .status(typePrint.getStatus())
                .build();
    }

    public void disableTypePrint (Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));
        typePrint.setStatus(EnumStatus.INACTIVE);
        typePrintRepository.save(typePrint);
    }

    public void deleteTypePrint(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));
        typePrint.setStatus(EnumStatus.INACTIVE);
        typePrint.setIsDeleted(true);
        typePrintRepository.save(typePrint);
    }

    public List<TypePrint> getTypePrintAll (){
        List<TypePrint> list = typePrintRepository.findAll()
                .stream()
                .filter(typePrint -> !typePrint.getIsDeleted())
                .toList();
        return list;
    }
}
