package Project.example.Project_1.controller;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.request.CategoryRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.CategoryResponse;
import Project.example.Project_1.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all Category", description = "API Category")
    public ApiResponse<List<Category>> getAllCategory() {
        return ApiResponse.<List<Category>>builder()
                .code(HttpStatus.OK.value())
                .message("Get all successfully")
                .result(service.getAllCategory())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Category by Id", description = "API Category")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully")
                .result(service.getById(id))
                .build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Category", description = "API create Category")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) throws Throwable {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Create Category successfully")
                .result(service.createCategory(request))
                .build();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Category", description = "API retrieve Category ")
    public ApiResponse<CategoryResponse> updateCategory(@RequestBody @Valid CategoryRequest request) throws Throwable {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update Category successfully")
                .result(service.updateCategory(request))
                .build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change status Category", description = "API change a status Category ")
    public ApiResponse<Void> disableCategory(@PathVariable Long id) {
        service.disableCategory(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Changer Status successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete Category ", description = "API delete Category ")
    public ApiResponse<Void> deleteCategory (@PathVariable Long id) {
        service.deleteCategory(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Delete Category successfully")
                .build();
    }


}

