package Project.example.Project_1.controller;

import Project.example.Project_1.enity.Product;
import Project.example.Project_1.enity.Size;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.service.SizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
@RequiredArgsConstructor
@Tag(name = "Size Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class SizeController {
    @Autowired
    SizeService sizeService;

    @GetMapping("/{productId}")
    @Operation(summary = "Lấy thông tin chi tiết sản phẩm")
    public ApiResponse<List<Size>> getSizeById(@PathVariable String productId) {
        return ApiResponse.<List<Size>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin sản phẩm thành công")
                .result(sizeService.getSizeByProduct(productId))
                .build();
    }
}
