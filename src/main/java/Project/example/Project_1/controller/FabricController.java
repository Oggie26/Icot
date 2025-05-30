package Project.example.Project_1.controller;

import Project.example.Project_1.request.FabricCreationRequest;
import Project.example.Project_1.request.FabricUpdateRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.FabricResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.service.FabricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/fabric")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
@Tag(name = "Fabric Controller")
public class FabricController {

    @Autowired
    FabricService fabricService;

    // SEARCH
    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm theo tên", description = "API Tìm kiếm theo tên FabricName")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponse<FabricResponse>> searchFabric(
            @RequestParam(defaultValue = "") String key,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<FabricResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Tìm kiếm theo tên FabricName thành công")
                .result(fabricService.searchFabric(key, page, size))
                .build();
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Tạo Fabric mới")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FabricResponse> createFabric(@Valid @RequestBody FabricCreationRequest request) {
        return ApiResponse.<FabricResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo Fabric thành công")
                .result(fabricService.createFabric(request))
                .build();
    }

    // UPDATE
    @PutMapping
    @Operation(summary = "Cập nhật thông tin Fabric")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<FabricResponse> updateFabric(@Valid @RequestBody FabricUpdateRequest request) {
        return ApiResponse.<FabricResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật Fabric thành công")
                .result(fabricService.updateFabric(request))
                .build();
    }

    // DELETE (soft delete)
    @DeleteMapping("/{id}")
    @Operation(summary = "Xoá Fabric (soft delete)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFabric(@PathVariable Long id) {
        fabricService.deleteFabric(id);
    }

    // CHANGE STATUS
    @PatchMapping("/status/{id}")
    @Operation(summary = "Thay đổi trạng thái ACTIVE/INACTIVE của Fabric")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> changeStatus(@PathVariable Long id) {
        fabricService.changeStatus(id);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Thay đổi trạng thái thành công")
                .result("SUCCESS")
                .build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết Fabric theo ID")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<FabricResponse> getFabricById(@PathVariable Long id) {
        return ApiResponse.<FabricResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy chi tiết Fabric thành công")
                .result(fabricService.getFabricById(id))
                .build();
    }

    // GET ALL (trạng thái ACTIVE)
    @GetMapping("/all")
    @Operation(summary = "Lấy tất cả Fabric đang ACTIVE")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<FabricResponse>> getAllActiveFabrics() {
        List<FabricResponse> responses = fabricService.getAllFabrics().stream()
                .map(fabric -> FabricResponse.builder()
                        .id(fabric.getId())
                        .fabricName(fabric.getFabricName())
                        .price(fabric.getPrice())
                        .status(fabric.getStatus())
                        .build())
                .toList();

        return ApiResponse.<List<FabricResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách Fabric ACTIVE thành công")
                .result(responses)
                .build();
    }
}
