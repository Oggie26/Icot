package Project.example.Project_1.controller;

import Project.example.Project_1.request.ProductUpdateRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.response.ProductResponse;
import Project.example.Project_1.response.ProductSearchRequest;
import Project.example.Project_1.service.ProductService;
import Project.example.Project_1.request.ProductCreateRequest;
import Project.example.Project_1.enity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping
    @Operation(summary = "Tạo sản phẩm mới")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo sản phẩm thành công")
                .result(productService.createProduct(request))
                .build();
    }

    @PutMapping()
    @Operation(summary = "Cập nhật sản phẩm")
    public ApiResponse<ProductResponse> updateProduct(
            @RequestBody ProductUpdateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật sản phẩm thành công")
                .result(productService.updateProduct(request))
                .build();
    }

    // Xóa sản phẩm (mềm)
    @DeleteMapping("/{productId}")
    @Operation(summary = "Xóa sản phẩm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xóa sản phẩm thành công")
                .build();
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Thay đổi trạng thái sản phẩm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> disableProduct(@PathVariable String productId) {
        productService.disableProduct(productId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Thay đổi trạng thái thành công")
                .build();
    }
//
//    // Lấy thông tin chi tiết sản phẩm
    @GetMapping("/{productId}")
    @Operation(summary = "Lấy thông tin chi tiết sản phẩm")
    public ApiResponse<Product> getProductById(@PathVariable String productId) {
        return ApiResponse.<Product>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin sản phẩm thành công")
                .result(productService.getById(productId))
                .build();
    }

    @GetMapping()
    @Operation(summary = "Lấy tất cả thông tin chi tiết sản phẩm")
    public ApiResponse<List<Product>> getProduct() {
        return ApiResponse.<List<Product>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy tất cả thông tin chi tiết sản phẩm")
                .result(productService.getProducts())
                .build();
    }
//
//    // Tìm kiếm sản phẩm (có phân trang)
//    @GetMapping("/search")
//    @Operation(summary = "Tìm kiếm sản phẩm")
//    public ApiResponse<PageResponse<ProductResponse>> searchProducts(
//            @Parameter(description = "Yêu cầu tìm kiếm sản phẩm") ProductSearchRequest request,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return ApiResponse.<PageResponse<ProductResponse>>builder()
//                .code(HttpStatus.OK.value())
//                .message("Tìm kiếm sản phẩm thành công")
//                .result(productService.searchProducts(request, page, size))
//                .build();
//    }

}
