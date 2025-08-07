package Project.example.Project_1.controller;

import Project.example.Project_1.enity.TypePrint;
import Project.example.Project_1.request.TypePrintRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.TypePrintResponse;
import Project.example.Project_1.service.TypePrintService;
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
@RequestMapping("/api/typePrint")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
@Tag(name = "TypePrint Controller")
public class TypePrintController {

    @Autowired
    TypePrintService typePrintService;

    // CREATE
    @PostMapping
    @Operation(summary = "Tạo TypePrint mới")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TypePrintResponse> createTypePrint(@Valid @RequestBody TypePrintRequest request) {
        return ApiResponse.<TypePrintResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo TypePrint thành công")
                .result(typePrintService.createTypePrint(request))
                .build();
    }

    // UPDATE(
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin TypePrint")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TypePrintResponse> updateTypePrint(@Valid @RequestBody TypePrintRequest request, @PathVariable Long id ) {
        return ApiResponse.<TypePrintResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật TypePrint thành công")
                .result(typePrintService.updateTypePrint(request, id))
                .build();
    }

    // DELETE (soft delete)
    @DeleteMapping("/{id}")
    @Operation(summary = "Xoá TypePrint (soft delete)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypePrint(@PathVariable Long id) {
        typePrintService.deleteTypePrint(id);
    }

    // CHANGE STATUS
    @PatchMapping("/status/{id}")
    @Operation(summary = "Thay đổi trạng thái ACTIVE/INACTIVE của TypePrint")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> changeStatus(@PathVariable Long id) {
        typePrintService.disableTypePrint(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Thay đổi trạng thái thành công")
                .build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết TypePrint theo ID")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TypePrintResponse> getFabricById(@PathVariable Long id) {
        return ApiResponse.<TypePrintResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy chi tiết TypePrint thành công")
                .result(typePrintService.getTypePrintById(id))
                .build();
    }

    // GET ALL (trạng thái ACTIVE)
    @GetMapping("/all")
    @Operation(summary = "Lấy tất cả Fabric đang ACTIVE")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<TypePrint>> getAllActiveFabrics() {
        return ApiResponse.<List<TypePrint>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách TypePrint ACTIVE thành công")
                .result(typePrintService.getTypePrintAll())
                .build();
    }
}
