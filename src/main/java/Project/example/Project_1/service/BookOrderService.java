package Project.example.Project_1.service;

import Project.example.Project_1.enity.*;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumOrderType;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.*;
import Project.example.Project_1.request.BookOrderCreateRequest;
import Project.example.Project_1.request.BookOrderUpdateRequest;
import Project.example.Project_1.response.BookOrderResponse;
import Project.example.Project_1.response.FabricResponse;
import Project.example.Project_1.response.PageResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    ProcessOrderRepository processOrderRepository;

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
        ProcessOrder processOrder = new ProcessOrder();
        processOrder.setType(EnumOrderType.BOOKORDER);
        processOrder.setProcessBookOrder(EnumBookOrder.PENDING);
        processOrder.setUser(user);
        processOrder.setBookOrder(bookOrder);
        processOrder.setIsDeleted(false);
        processOrderRepository.save(processOrder);
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

    @Transactional
    public BookOrderResponse updateBookOrder(BookOrderUpdateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();

        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));

        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(request.getFabricId())
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND));

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(request.getTypePrint())
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));

        bookOrder.setSize(request.getSize());
        bookOrder.setQuantity(request.getQuantity());
        bookOrder.setColor(request.getColor());
        bookOrder.setDescription(request.getDescription());
        bookOrder.setUser(user);
        bookOrder.setCategory(category);
        bookOrder.setFabric(fabric);
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

    public void cancelBookOrder(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));
        bookOrder.setIsDeleted(true);
        bookOrderRepository.save(bookOrder);
    }

    public BookOrderResponse getBookOrderById(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));
        return BookOrderResponse.builder()
                .id(bookOrder.getId())
                .size(bookOrder.getSize())
                .category(bookOrder.getCategory())
                .color(bookOrder.getColor())
                .quantity(bookOrder.getQuantity())
                .totalPrice(bookOrder.getTotalPrice())
                .fabric(bookOrder.getFabric())
                .description(bookOrder.getDescription())
                .typePrint(bookOrder.getTypePrint())
                .user(bookOrder.getUser())
                .build();
    }

    public List<BookOrder> getMyBookOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<BookOrder> bookOrders = bookOrderRepository.findBookOrderByUser(user)
                .stream()
                .filter(e -> !e.getIsDeleted())
                .toList();
        return bookOrders;
    }

    public PageResponse<BookOrderResponse> searchBookOrderByPhone(String key, int page, int size) {
        if (key == null || key.trim().isEmpty()) {
            key = "";
        }

        if (page < 0) page = 0;
        if (size < 1) size = 10;

        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<BookOrder> bookOrderPage = bookOrderRepository.findByUser_PhoneContainingIgnoreCase(key, pageable);

        List<BookOrderResponse> responses = bookOrderPage.getContent().stream()
                .map(order -> BookOrderResponse.builder()
                        .id(order.getId())
                        .size(order.getSize())
                        .quantity(order.getQuantity())
                        .description(order.getDescription())
                        .color(order.getColor())
                        .totalPrice(order.getTotalPrice())
                        .category(order.getCategory())
                        .fabric(order.getFabric())
                        .typePrint(order.getTypePrint())
                        .user(order.getUser()) // ⚠ Nếu trả về FE thì nên dùng @JsonIgnore trong User hoặc DTO
                        .build())
                .collect(Collectors.toList());

        return new PageResponse<>(
                responses,
                bookOrderPage.getNumber(),
                bookOrderPage.getTotalPages(),
                bookOrderPage.getTotalElements()
        );
    }
    public BookOrder AsignTask(BookOrder bookOrder){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        return null;
    }
}
