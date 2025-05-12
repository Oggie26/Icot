package Project.example.Project_1.service;

import Project.example.Project_1.config.PageMapper;
import Project.example.Project_1.enity.Product;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.ProductRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PageMapper pageMapper;

    private void validateNewProduct(ProductCreateRequest request) {
        if(request.getProductName().isEmpty() || request.getProductName().isBlank() ||
                containsSpecialCharacter(request.getProductName())) {
            throw new AppException(ErrorCode.INVALID_PRODUCT_NAME);
        }
        if(request.getPrice() <= 0 || request.getPrice() == null){
            throw new AppException(ErrorCode.INVALID_PRODUCT_PRICE);
        }
        if(request.getCategory() == null){
            throw new AppException(ErrorCode.INVALID_PRODUCT_CATEGORY);
        }
        if(request.getDescription().isEmpty() || request.getDescription().isBlank() ||
                containsSpecialCharacter(request.getDescription())) {
            throw new AppException(ErrorCode.INVALID_PRODUCT_DESCRIPTION);
        }
        if(request.getSize() == null){
            throw new AppException(ErrorCode.INVALID_PRODUCT_SIZE);
        }
        if(request.getImageThumbnail() == null || request.getImageThumbnail().isEmpty()){
            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
        }
        if(request.getImages() == null || request.getImages().isEmpty()){
            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
        }
    }

    private void validateUpdatedProduct(ProductUpdateRequest request) {
        if(request.getProductName().isEmpty() || request.getProductName().isBlank() ||
                containsSpecialCharacter(request.getProductName())) {
            throw new AppException(ErrorCode.INVALID_PRODUCT_NAME);
        }
        if(request.getPrice() <= 0 || request.getPrice() == null){
            throw new AppException(ErrorCode.INVALID_PRODUCT_PRICE);
        }
        if(request.getCategory() == null){
            throw new AppException(ErrorCode.INVALID_PRODUCT_CATEGORY);
        }
        if(request.getDescription().isEmpty() || request.getDescription().isBlank() ||
                containsSpecialCharacter(request.getDescription())) {
            throw new AppException(ErrorCode.INVALID_PRODUCT_DESCRIPTION);
        }
        if(request.getSize() == null){
            throw new AppException(ErrorCode.INVALID_PRODUCT_SIZE);
        }
        if(request.getImageThumbnail() == null || request.getImageThumbnail().isEmpty()){
            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
        }
        if(request.getImages() == null || request.getImages().isEmpty()){
            throw new AppException(ErrorCode.INVALID_PRODUCT_IMAGE);
        }
    }

    public boolean containsSpecialCharacter(String input) {
        // Regex tìm bất kỳ ký tự không phải chữ cái, chữ số, khoảng trắng hoặc dấu gạch dưới
        String specialCharPattern = "[^a-zA-Z0-9_\\s]";
        return input != null && input.matches(".*" + specialCharPattern + ".*");
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        validateNewProduct(request);

        Product product = modelMapper.map(request, Product.class);
        product.setIsDeleted(false);
        product.setStatus(EnumStatus.ACTIVE);

        product = productRepository.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }

    public ProductResponse updateProduct(String productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        validateUpdatedProduct(request);
        modelMapper.map(request, product);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }

    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        product.setIsDeleted(true);
        product.setStatus(EnumStatus.DELETED);
        productRepository.save(product);
    }

    public PageResponse<ProductResponse> searchProducts(ProductSearchRequest request, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Tìm kiếm theo tên sản phẩm (không phân biệt hoa thường)
            if (request.getProductName() != null && !request.getProductName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + request.getProductName().toLowerCase() + "%"));
            }

            // Tìm kiếm theo khoảng giá
            if (request.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }
            if (request.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }

            // Tìm kiếm theo kích thước
            if (request.getSize() != null) {
                predicates.add(cb.equal(root.get("size"), request.getSize()));
            }

            // Tìm kiếm theo trạng thái
            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }

            // Tìm kiếm theo danh mục
            if (request.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), request.getCategoryId()));
            }

            // Tìm kiếm theo thời gian tạo
            if (request.getFromCreatedAt() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), request.getFromCreatedAt()));
            }
            if (request.getToCreatedAt() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), request.getToCreatedAt()));
            }

            // Lọc sản phẩm chưa bị xóa (hoặc theo yêu cầu tìm kiếm)
            if (request.getIsDeleted() != null) {
                predicates.add(cb.equal(root.get("isDeleted"), request.getIsDeleted()));
            } else {
                // Mặc định là chưa bị xóa
                predicates.add(cb.equal(root.get("isDeleted"), false));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Product> productPage = productRepository.findAll(spec, pageable);
        return pageMapper.convertToPageResponse(productPage, product -> modelMapper.map(product, ProductResponse.class));
    }

    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return modelMapper.map(product, ProductResponse.class);
    }


}
