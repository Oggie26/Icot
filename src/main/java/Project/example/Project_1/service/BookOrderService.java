package Project.example.Project_1.service;

import Project.example.Project_1.enity.*;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumOrderType;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.*;
import Project.example.Project_1.request.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookOrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageDesignRepository imageDesignRepository;


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

    @Autowired
    AddressRepository addressRepository;

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

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(request.getTypePrintId())
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        BookOrder bookOrder = new BookOrder();
        bookOrder.setSize(request.getSize());
        bookOrder.setQuantity(request.getQuantity());
        bookOrder.setColor(request.getColor());
        bookOrder.setDescription(request.getDescription());
        bookOrder.setUser(user);
        bookOrder.setAddress(address);
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
                .address(bookOrder.getAddress())
                .customerName(bookOrder.getCustomerName())
                .imageSkins(imageList)
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

        Address address = addressRepository.findById(request.getAddressId())
                        .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        bookOrder.setSize(request.getSize());
        bookOrder.setQuantity(request.getQuantity());
        bookOrder.setColor(request.getColor());
        bookOrder.setDescription(request.getDescription());
        bookOrder.setUser(user);
        bookOrder.setCategory(category);
        bookOrder.setFabric(fabric);
        bookOrder.setAddress(address);
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
                .address(bookOrder.getAddress())
                .customerName(bookOrder.getCustomerName())
                .build();
    }

    public void cancelBookOrder(Long id,CancelRequest cancelRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));
        bookOrder.setStatus(EnumBookOrder.CANCELED);
        bookOrder.setResponse(cancelRequest.getResponse());
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
                .imageSkins(bookOrder.getImageCus())
                .imageDesigns(bookOrder.getImageDesign())
                .quantity(bookOrder.getQuantity())
                .totalPrice(bookOrder.getTotalPrice())
                .fabric(bookOrder.getFabric())
                .customerName(bookOrder.getCustomerName())
                .description(bookOrder.getDescription())
                .typePrint(bookOrder.getTypePrint())
                .designName(bookOrder.getDesignName())
                .user(bookOrder.getUser())
                .createdDate(bookOrder.getCreatedAt())
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
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(bookOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        EnumBookOrder currentStatus = bookOrder.getStatus();

        if (currentStatus == EnumBookOrder.PAYMENT) {
            if (request.getDesignName() == null || request.getDesignName().isEmpty()) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
            bookOrder.setDesignName(request.getDesignName());
            bookOrder.setStatus(EnumBookOrder.ASSIGNED_TASK);

        } else if (currentStatus == EnumBookOrder.ASSIGNED_TASK) {
            List<ImageDesign> imageDesignList = new ArrayList<>();

            if (request.getDesignId() == null) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }

            if (request.getImageDesign() != null && !request.getImageDesign().isEmpty()) {
                imageDesignList = request.getImageDesign().stream()
                        .map(imageRequest -> {
                            ImageDesign imageDesign = new ImageDesign();
                            imageDesign.setImage(imageRequest.getImage());
                            imageDesign.setIsDeleted(false);
                            imageDesign.setBookOrder(bookOrder);
                            return imageDesign;
                        }).collect(Collectors.toList());
            }
            imageDesignRepository.saveAll(imageDesignList);
            bookOrder.setStatus(EnumBookOrder.DELIVERY);

        } else if (currentStatus == EnumBookOrder.CUSTOMER_ACCEPTED) {
            bookOrder.setStatus(EnumBookOrder.DELIVERY);

        } else if (currentStatus == EnumBookOrder.CUSTOMER_REJECTED) {
            if (request.getResponse() == null || request.getResponse().isEmpty()) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
            bookOrder.setResponse(request.getResponse());
            bookOrder.setStatus(EnumBookOrder.ASSIGNED_TASK);

        } else if (currentStatus == EnumBookOrder.DELIVERY) {
            bookOrder.setImageDelivery(bookOrder.getImageDelivery());
            bookOrder.setStatus(EnumBookOrder.FINISHED);
        } else {
            throw new AppException(ErrorCode.INVALID_STATUS);
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


//    public void deliveryBookOrder(Long bookOrderId, MultipartFile imageDelivery) {
//        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(bookOrderId)
//                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));
//
//        // Đường dẫn tuyệt đối để lưu file
//        String uploadDir = System.getProperty("user.dir") + "/uploads/delivery"; // Thư mục nằm trong project
//        String fileName = UUID.randomUUID() + "_" + imageDelivery.getOriginalFilename();
//
//        File uploadPath = new File(uploadDir);
//        if (!uploadPath.exists() && !uploadPath.mkdirs()) {
//            throw new RuntimeException("Không thể tạo thư mục lưu file: " + uploadDir);
//        }
//
//        File destination = new File(uploadPath, fileName);
//        try {
//            imageDelivery.transferTo(destination);
//        } catch (IOException e) {
//            throw new RuntimeException("Lỗi khi lưu file ảnh giao hàng", e);
//        }
//
//        // Gán đường dẫn tương đối để truy cập qua browser
//        bookOrder.setImageDelivery("/uploads/delivery/" + fileName);
//        bookOrder.setStatus(EnumBookOrder.FINISHED);
//        bookOrderRepository.save(bookOrder);
//    }

    public void deliveryBookOrder(Long bookOrderId, ImageDeliveryRequest request) {
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(bookOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));
        bookOrder.setImageDelivery(request.getImageDelivery());
        bookOrder.setStatus(EnumBookOrder.FINISHED);
        bookOrderRepository.save(bookOrder);
    }

    public void designerUploadInfo(Long bookOrderId, DesignerUploadRequest request) {
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(bookOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));

        // Xử lý danh sách thiết kế
        if (request.getImageDesign() != null && !request.getImageDesign().isEmpty()) {
            List<ImageDesign> imageDesignList = request.getImageDesign().stream()
                    .map(imageRequest -> {
                        ImageDesign imageDesign = new ImageDesign();
                        imageDesign.setImage(imageRequest.getImage());
                        imageDesign.setIsDeleted(false);
                        imageDesign.setBookOrder(bookOrder);
                        return imageDesign;
                    }).collect(Collectors.toList());

            imageDesignRepository.saveAll(imageDesignList);
            bookOrder.setStatus(EnumBookOrder.CUSTOMER_RECEIVED);
        }
        bookOrderRepository.save(bookOrder);
    }




    public List<BookOrderResponse> getBookOrders() {
        List<BookOrder> list = bookOrderRepository.findAll();

        List<BookOrderResponse> listResponse = new ArrayList<>();

        for (BookOrder bookOrder : list) {
            BookOrderResponse response = BookOrderResponse.builder()
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
                    .enumBookOrder(bookOrder.getStatus())
                    .address(bookOrder.getAddress())
                    .customerName(bookOrder.getCustomerName())
                    .imageSkins(bookOrder.getImageCus())
                    .createdDate(bookOrder.getCreatedAt())
                    .designName(bookOrder.getDesignName())
                    .build();
            listResponse.add(response);
        }

        return listResponse;
    }

    public List<BookOrderResponse> getMyBookOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        List<BookOrder> bookOrders = bookOrderRepository.findBookOrderByUser(user);
        List<BookOrderResponse> responseList = new ArrayList<>();

        for (BookOrder bookOrder : bookOrders) {
            // Lấy danh sách imageDesign theo bookOrder nếu cần
            List<ImageDesign> imageDesigns = imageDesignRepository.findByBookOrder(bookOrder);

            BookOrderResponse response = BookOrderResponse.builder()
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
                    .enumBookOrder(bookOrder.getStatus())
                    .address(bookOrder.getAddress())
                    .customerName(bookOrder.getCustomerName())
                    .imageSkins(bookOrder.getImageCus())
                    .imageDesigns(imageDesigns) // Gán danh sách hình thiết kế
                    .createdDate(bookOrder.getCreatedAt())
                    .designName(bookOrder.getDesignName())
                    .build();

            responseList.add(response);
        }

        return responseList;
    }


    public void paymentSuccess(Long id){
        BookOrder bookOrder = bookOrderRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKORDER_NOT_FOUND));

        if(bookOrder.getStatus().equals(EnumBookOrder.PENDING)){
            if (!bookOrder.getIsDeleted()){
                bookOrder.setStatus(EnumBookOrder.PAYMENT);
            }
        }
        bookOrderRepository.save(bookOrder);
    }


}
