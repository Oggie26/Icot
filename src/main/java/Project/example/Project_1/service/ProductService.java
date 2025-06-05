package Project.example.Project_1.service;

import Project.example.Project_1.config.PageMapper;
import Project.example.Project_1.enity.*;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.enums.EnumOrderType;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.*;
import Project.example.Project_1.request.ProductCreateRequest;
import Project.example.Project_1.request.ProductUpdateRequest;
import Project.example.Project_1.response.ProductResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.response.ProductSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FabricRepository fabricRepository;

    @Autowired
    TypePrintRepository typePrintRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    SizeRepository sizeRepository;

//    @Autowired
//    ModelMapper modelMapper;
//    @Autowired
//    PageMapper pageMapper;
//
//    private void validateNewProduct(ProductCreateRequest request) {
//        if(request.getProductName().isEmpty() || request.getProductName().isBlank() ||
//                containsSpecialCharacter(request.getProductName())) {
//            throw new AppException(ErrorCode.INVALID_PRODUCT_NAME);
//        }
//        if(request.getPrice() <= 0 || request.getPrice() == null){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_PRICE);
//        }
//        if(request.getCategory() == null){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_CATEGORY);
//        }
//        if(request.getDescription().isEmpty() || request.getDescription().isBlank() ||
//                containsSpecialCharacter(request.getDescription())) {
//            throw new AppException(ErrorCode.INVALID_PRODUCT_DESCRIPTION);
//        }
//        if(request.getSize() == null){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_SIZE);
//        }
//        if(request.getImageThumbnail() == null || request.getImageThumbnail().isEmpty()){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
//        }
//        if(request.getImages() == null || request.getImages().isEmpty()){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
//        }
//    }
//
//    private void validateUpdatedProduct(ProductUpdateRequest request) {
//        if(request.getProductName().isEmpty() || request.getProductName().isBlank() ||
//                containsSpecialCharacter(request.getProductName())) {
//            throw new AppException(ErrorCode.INVALID_PRODUCT_NAME);
//        }
//        if(request.getPrice() <= 0 || request.getPrice() == null){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_PRICE);
//        }
//        if(request.getCategory() == null){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_CATEGORY);
//        }
//        if(request.getDescription().isEmpty() || request.getDescription().isBlank() ||
//                containsSpecialCharacter(request.getDescription())) {
//            throw new AppException(ErrorCode.INVALID_PRODUCT_DESCRIPTION);
//        }
//        if(request.getSize() == null){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_SIZE);
//        }
//        if(request.getImageThumbnail() == null || request.getImageThumbnail().isEmpty()){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
//        }
//        if(request.getImages() == null || request.getImages().isEmpty()){
//            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
//        }
//    }
//
//    public boolean containsSpecialCharacter(String input) {
//        // Regex tìm bất kỳ ký tự không phải chữ cái, chữ số, khoảng trắng hoặc dấu gạch dưới
//        String specialCharPattern = "[^a-zA-Z0-9_\\s]";
//        return input != null && input.matches(".*" + specialCharPattern + ".*");
//    }
//
//    public ProductResponse createProduct(ProductCreateRequest request) {
//        validateNewProduct(request);
//
//        Product product = modelMapper.map(request, Product.class);
//        product.setIsDeleted(false);
//        product.setStatus(EnumStatus.ACTIVE);
//
//        product = productRepository.save(product);
//        return modelMapper.map(product, ProductResponse.class);
//    }
//
//    public ProductResponse updateProduct(String productId, ProductUpdateRequest request) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
//        validateUpdatedProduct(request);
//        modelMapper.map(request, product);
//        product = productRepository.save(product);
//        return modelMapper.map(product, ProductResponse.class);
//    }
//
//    public void deleteProduct(String productId) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//        product.setIsDeleted(true);
//        product.setStatus(EnumStatus.DELETED);
//        productRepository.save(product);
//    }
//
//    public PageResponse<ProductResponse> searchProducts(ProductSearchRequest request, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//
//        Specification<Product> spec = (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            // Tìm kiếm theo tên sản phẩm (không phân biệt hoa thường)
//            if (request.getProductName() != null && !request.getProductName().isEmpty()) {
//                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + request.getProductName().toLowerCase() + "%"));
//            }
//
//            // Tìm kiếm theo khoảng giá
//            if (request.getMinPrice() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
//            }
//            if (request.getMaxPrice() != null) {
//                predicates.add(cb.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
//            }
//
//            // Tìm kiếm theo kích thước
//            if (request.getSize() != null) {
//                predicates.add(cb.equal(root.get("size"), request.getSize()));
//            }
//
//            // Tìm kiếm theo trạng thái
//            if (request.getStatus() != null) {
//                predicates.add(cb.equal(root.get("status"), request.getStatus()));
//            }
//
//            // Tìm kiếm theo danh mục
//            if (request.getCategoryId() != null) {
//                predicates.add(cb.equal(root.get("category").get("id"), request.getCategoryId()));
//            }
//
//            // Tìm kiếm theo thời gian tạo
//            if (request.getFromCreatedAt() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), request.getFromCreatedAt()));
//            }
//            if (request.getToCreatedAt() != null) {
//                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), request.getToCreatedAt()));
//            }
//
//            // Lọc sản phẩm chưa bị xóa (hoặc theo yêu cầu tìm kiếm)
//            if (request.getIsDeleted() != null) {
//                predicates.add(cb.equal(root.get("isDeleted"), request.getIsDeleted()));
//            } else {
//                // Mặc định là chưa bị xóa
//                predicates.add(cb.equal(root.get("isDeleted"), false));
//            }
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//
//        Page<Product> productPage = productRepository.findAll(spec, pageable);
//        return pageMapper.convertToPageResponse(productPage, product -> modelMapper.map(product, ProductResponse.class));
//    }
//
//    public ProductResponse getProductById(String productId) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
//        return modelMapper.map(product, ProductResponse.class);
//    }

    public ProductResponse createProduct(ProductCreateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if(productRepository.findByProductName(request.getProductName()).isPresent()){
            throw new AppException(ErrorCode.PRODUCT_NAME_EXISTED);
        }
        if(request.getPrice() < 0){
            throw new AppException(ErrorCode.INVALID_PRODUCT_PRICE);
        }
        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(request.getFabricId())
                .orElseThrow( () -> new AppException(ErrorCode.FABRIC_NOT_FOUND));

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(request.getTypePrintId())
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));

        Product product = Product.builder()
                .productName(request.getProductName())
                .color(request.getColor())
                .description(request.getDescription())
                .imageThumbnail(request.getImageThumbnail())
                .price(request.getPrice())
                .status(EnumStatus.ACTIVE)
                .category(category)
                .fabric(fabric)
                .typePrint(typePrint)
                .build();

        productRepository.save(product);

        List<Image> imageList = new ArrayList<>();
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            imageList = request.getImages().stream()
                    .map(imageSkinRequest -> {
                        Image image = new Image();
                        image.setImage(imageSkinRequest.getImage());
                        image.setProduct(product);
                        image.setIsDeleted(false);
                        return image;
                    })
                    .toList();
        }

        for (Image image : imageList) {
            image.setProduct(product);
        }


        List<Size> sizeList = new ArrayList<>();
        if (request.getSizes() != null && !request.getSizes().isEmpty()) {
            sizeList = request.getSizes().stream()
                    .map(sizes -> {
                        Size size = new Size();
                        size.setSize(sizes.getSize());
                        size.setProduct(product);
                        return size;
                    })
                    .toList();
        }

        for (Image image : imageList) {
            image.setProduct(product);
        }
        product.setIsDeleted(false);
        imageRepository.saveAll(imageList);
        sizeRepository.saveAll(sizeList);


        return  ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdAt(product.getCreatedAt())
                .price(product.getPrice())
                .sizes(product.getSizes())
                .imageThumbnail(product.getImageThumbnail())
                .images(product.getImages())
                .feedbacks(product.getFeedbacks())
                .category(product.getCategory())
                .status(product.getStatus())
                .sizes(product.getSizes())
                .fabric(product.getFabric())
                .images(product.getImages())
                .typePrint(product.getTypePrint())
                .description(product.getDescription())
                .build();
    }

    public List<Product> getProducts(){
        List<Product> products= productRepository.findAll()
                .stream()
                .filter(product -> !product.getIsDeleted())
                .toList();
        return products;
    }

    public void disableProduct(String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(EnumStatus.INACTIVE);
        productRepository.save(product);
    }

    public void deleteProduct(String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setIsDeleted(false);
        productRepository.save(product);
    }

    public ProductResponse updateProduct(ProductUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (request.getPrice() < 0) {
            throw new AppException(ErrorCode.INVALID_PRODUCT_PRICE);
        }

        Product product = productRepository.findByIdAndIsDeletedFalse(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Kiểm tra nếu tên sản phẩm đã tồn tại (và không trùng với chính sản phẩm hiện tại)
        productRepository.findByProductName(request.getProductName()).ifPresent(existingProduct -> {
            if (!existingProduct.getId().equals(product.getId())) {
                throw new AppException(ErrorCode.PRODUCT_NAME_EXISTED);
            }
        });

        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Fabric fabric = fabricRepository.findByIdAndIsDeletedFalse(request.getFabricId())
                .orElseThrow(() -> new AppException(ErrorCode.FABRIC_NOT_FOUND));

        TypePrint typePrint = typePrintRepository.findByIdAndIsDeletedFalse(request.getTypePrintId())
                .orElseThrow(() -> new AppException(ErrorCode.TYPEPRINT_NOT_FOUNT));

        // Cập nhật thông tin sản phẩm
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setColor(request.getColor());
        product.setImageThumbnail(request.getImageThumbnail());
        product.setCategory(category);
        product.setFabric(fabric);
        product.setTypePrint(typePrint);
        product.setIsDeleted(false);

        // Cập nhật lại danh sách hình ảnh
        List<Image> newImages = request.getImages() != null ? request.getImages().stream()
                .map(imageReq -> {
                    Image image = new Image();
                    image.setImage(imageReq.getImage());
                    image.setProduct(product);
                    image.setIsDeleted(false);
                    return image;
                })
                .toList() : new ArrayList<>();

        imageRepository.saveAll(newImages);

        // Cập nhật lại danh sách kích thước
        List<Size> newSizes = request.getSizes() != null ? request.getSizes().stream()
                .map(sizeReq -> {
                    Size size = new Size();
                    size.setSize(sizeReq.getSize());
                    size.setProduct(product);
                    return size;
                })
                .toList() : new ArrayList<>();

        sizeRepository.saveAll(newSizes);

        // Lưu sản phẩm
        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdAt(product.getCreatedAt())
                .price(product.getPrice())
                .sizes(product.getSizes())
                .imageThumbnail(product.getImageThumbnail())
                .images(product.getImages())
                .feedbacks(product.getFeedbacks())
                .category(product.getCategory())
                .typePrint(product.getTypePrint())
                .description(product.getDescription())
                .build();
    }

    public Product getById(String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return product;
    }


}
