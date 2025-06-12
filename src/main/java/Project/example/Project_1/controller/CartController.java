package Project.example.Project_1.controller;

import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.CartResponse;
import Project.example.Project_1.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@Tag(name = "Cart Controller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    @Operation(summary = "Thêm sản phẩm vào giỏ hàng", description = "API Thêm sản phẩm vào giỏ hàng")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> addItemToCart(
            @RequestParam String productId, @RequestParam(defaultValue = "1" ) @Min(1) int quantity) {
        cartService.addProductToCart(productId, quantity);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Thêm sản phẩm vào giỏ hàng thành công")
                .build();
    }
    @GetMapping
    @Operation(summary = "Xem giỏ hàng", description = "API Xem giỏ hàng")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CartResponse> getCart() {
        return ApiResponse.<CartResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Xem giỏ hàng thành công")
                .result(cartService.getCart())
                .build();
    }
    @DeleteMapping("/remove")
    @Operation(summary = "Xóa sản phẩm khỏi giỏ hàng", description = "API Xóa sản phẩm khỏi giỏ hàng")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> removeProductFromCart(@RequestParam List<String> productIds) {
        cartService.removeProductFromCart(productIds);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xóa sản phẩm khỏi giỏ hàng thành công")
                .build();
    }
    @PatchMapping("/update-quantity")
    @Operation(summary = "Cập nhật số lượng sản phẩm trong giỏ hàng", description = "API Cập nhật số lượng sản phẩm trong giỏ hàng")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateQuantityCartItem(@RequestParam String productId, @RequestParam @Min(1) Integer quantity){
        cartService.updateProductQuantityInCart(productId, quantity);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật số lượng thành công")
                .build();
    }
}
