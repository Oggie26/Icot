package Project.example.Project_1.service;

import Project.example.Project_1.enity.*;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.*;
import Project.example.Project_1.request.BookOrderCreateRequest;
import Project.example.Project_1.response.BookOrderResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BookOrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FabricRepository fabricRepository;

    @Autowired
    TypePrintRepository typePrintRepository;

    @Autowired
    BookOrderRepository bookOrderRepository;

    @Transactional
    public BookOrderResponse bookOrder(BookOrderCreateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(request.getFabricId())
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND));

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(request.getTypePrint())
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));

        BookOrder bookOrder = new BookOrder();
        bookOrder.setSize(request.getSize());
        bookOrder.setQuantity(request.getQuantity());
        bookOrder.setColor(request.getColor());
        bookOrder.setDescription(request.getDescription());
        bookOrder.setUser(user);
        bookOrder.setCategory(category);
        bookOrder.setFabric(fabric);
        bookOrder.setIsDeleted(false);
        bookOrder.setTypePrint(typePrint);
        Double totalPrice = (((fabric.getPrice() + typePrint.getPrice()) / 0.3 ) * bookOrder.getQuantity());
        bookOrder.setTotalPrice(totalPrice);
        bookOrderRepository.save(bookOrder);
        return BookOrderResponse.builder()
                .id(bookOrder.getId())
                .size(bookOrder.getSize())
                .category(category)
                .color(bookOrder.getColor())
                .quantity(bookOrder.getQuantity())
                .totalPrice(bookOrder.getTotalPrice())
                .fabric(fabric)
                .description(bookOrder.getDescription())
                .typePrint(typePrint)
                .user(user)
                .build();
    }
}
