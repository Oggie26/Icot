package Project.example.Project_1.service;

import Project.example.Project_1.enity.*;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumOrderType;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.*;
import Project.example.Project_1.request.BookOrderCreateRequest;
import Project.example.Project_1.request.BookOrderUpdateRequest;
import Project.example.Project_1.request.ChangeStatus;
import Project.example.Project_1.response.BookOrderResponse;
import Project.example.Project_1.response.PageResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    DesignRepository designRepository;

    @Autowired
    ImageCusRepository imageCusRepository;

    @Transactional
    public BookOrderResponse bookOrder(BookOrderCreateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design");
        }
        String username = authentication.getName();
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Thiếu ID của Design"));
        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND, "Thiếu ID của Design"));

        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(request.getFabricId())
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND, "Thiếu ID của Design"));

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(request.getTypePrintId())
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT, "Thiếu ID của Design"));

        BookOrder bookOrder = new BookOrder();
        bookOrder.setSize(request.getSize());
        bookOrder.setQuantity(request.getQuantity());
        bookOrder.setColor(request.getColor());
        bookOrder.setDescription(request.getDescription());
        bookOrder.setUser(user);
        bookOrder.setCategory(category);
        bookOrder.setFabric(fabric);
        bookOrder.setIsDeleted(false);
        bookOrder.setCustomerName(user.getFullName());
        bookOrder.setTypePrint(typePrint);
        ProcessOrder processOrder = new ProcessOrder();
        processOrder.setType(EnumOrderType.BOOKORDER);
        processOrder.setProcessBookOrder(EnumBookOrder.PENDING);
        processOrder.setUser(user);
        processOrder.setBookOrder(bookOrder);
        processOrder.setIsDeleted(false);
        bookOrder.setStatus(EnumBookOrder.PENDING);
        Double totalPrice = (((fabric.getPrice() + typePrint.getPrice()) / 0.3 ) * bookOrder.getQuantity());
        bookOrder.setTotalPrice(totalPrice);


        List<ImageCus> imageList = new ArrayList<>();
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            imageList = request.getImage().stream()
                    .map(imageSkinRequest -> {
                        ImageCus imageCus = new ImageCus();
                        imageCus.setImage(imageSkinRequest.getImage());
                        imageCus.setUser(user);
                        imageCus.setIsDeleted(false);
                        return imageCus;
                    })
                    .collect(Collectors.toList());
        }

        for (ImageCus imageSkin : imageList) {
            imageSkin.setBookOrder(bookOrder);
        }
        processOrderRepository.save(processOrder);
        imageCusRepository.saveAll(imageList);
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
                .customerName(bookOrder.getCustomerName())
                .imageSkins(imageList)
                .build();
    }

    @Transactional
    public BookOrderResponse updateBookOrder(BookOrderUpdateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design");
        }
        String username = authentication.getName();

        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Thiếu ID của Design"));

        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND, "Thiếu ID của Design"));

        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND, "Thiếu ID của Design"));

        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(request.getFabricId())
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND, "Thiếu ID của Design"));

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(request.getTypePrint())
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT, "Thiếu ID của Design"));

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
                .customerName(bookOrder.getCustomerName())
                .build();
    }

    public void cancelBookOrder(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design");
        }
        String username = authentication.getName();
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND, "Thiếu ID của Design"));
        bookOrder.setIsDeleted(true);
        bookOrderRepository.save(bookOrder);
    }

    public BookOrderResponse getBookOrderById(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design");
        }
        String username = authentication.getName();
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND, "Thiếu ID của Design"));
        return BookOrderResponse.builder()
                .id(bookOrder.getId())
                .size(bookOrder.getSize())
                .category(bookOrder.getCategory())
                .color(bookOrder.getColor())
                .quantity(bookOrder.getQuantity())
                .totalPrice(bookOrder.getTotalPrice())
                .fabric(bookOrder.getFabric())
                .customerName(bookOrder.getCustomerName())
                .description(bookOrder.getDescription())
                .typePrint(bookOrder.getTypePrint())
                .user(bookOrder.getUser())
                .build();
    }

    public List<BookOrder> getMyBookOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design");
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Thiếu ID của Design"));
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
                        .imageSkins(order.getImageCus())
                        .user(order.getUser())
                        .build())
                .collect(Collectors.toList());

        return new PageResponse<>(
                responses,
                bookOrderPage.getNumber(),
                bookOrderPage.getTotalPages(),
                bookOrderPage.getTotalElements()
        );
    }
    public BookOrder changeStatus(ChangeStatus request, Long bookOrderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design");
        }

        String username = authentication.getName();
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(bookOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND, "Thiếu ID của Design"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED, "Thiếu ID của Design"));

        EnumBookOrder currentStatus = bookOrder.getStatus();

        switch (currentStatus) {
            case PAYMENT:
                // Gán tên designer và chuyển sang ASSIGNED_TASK
                if (request.getDesignName() == null || request.getDesignName().isEmpty()) {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Thiếu tên Designer");
                }
                bookOrder.setDesignName(request.getDesignName());
                bookOrder.setStatus(EnumBookOrder.ASSIGNED_TASK);
                break;

            case ASSIGNED_TASK:
                // Gán design (nếu chưa có)
                if (request.getDesignId() == null) {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Thiếu ID của Design");
                }

                Design design = designRepository.findByIdAndIsDeletedFalse(request.getDesignId())
                        .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND, "Thiếu ID của Design"));
                bookOrder.setDesign(design);
                bookOrder.setStatus(EnumBookOrder.CUSTOMER_RECEIVED);
                break;

            case CUSTOMER_REJECTED:
                // Cho phép phản hồi và quay lại trạng thái ASSIGNED_TASK
                if (request.getResponse() == null || request.getResponse().isEmpty()) {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Thiếu phản hồi từ người dùng");
                }
                bookOrder.setResponse(request.getResponse());
                bookOrder.setStatus(EnumBookOrder.ASSIGNED_TASK);
                break;

            default:
                throw new AppException(ErrorCode.INVALID_STATUS, "Không thể chuyển trạng thái ở bước hiện tại");
        }

        // Lưu lịch sử xử lý
        ProcessOrder processOrder = ProcessOrder.builder()
                .user(user)
                .time(LocalDate.now())
                .processBookOrder(bookOrder.getStatus())
                .build();

        processOrderRepository.save(processOrder);
        bookOrderRepository.save(bookOrder);
        return bookOrder;
    }

}
