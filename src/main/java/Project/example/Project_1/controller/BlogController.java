package Project.example.Project_1.controller;
import Project.example.Project_1.enity.Blog;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.request.BlogCreateRequest;
import Project.example.Project_1.request.BlogUpdateRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.BlogResponse;
import Project.example.Project_1.response.FabricResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
@Tag(name = "Blog Controller")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogController {
    @Autowired
    BlogService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all blog", description = "API blog")
    public ApiResponse<List<Blog>> getBlog() {
        return ApiResponse.<List<Blog>>builder()
                .code(HttpStatus.OK.value())
                .message("Get all successfully")
                .result(service.getALlBlog())
                .build();
    }

    @GetMapping("/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get blog by Id", description = "API blog")
    public ApiResponse<BlogResponse> getBlogById(@PathVariable Long blogId) {
        return ApiResponse.<BlogResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully")
                .result(service.getBlogById(blogId))
                .build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a Blog for ADMIN, MANAGER", description = "API create Blog ")
    public ApiResponse<BlogResponse> createBlog(@RequestBody @Valid BlogCreateRequest request) throws Throwable {
        return ApiResponse.<BlogResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Create Blog successfully")
                .result(service.createBlog(request))
                .build();
    }

    @PutMapping("/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a Blog for ADMIN, MANAGER", description = "API retrieve Blog ")
    public ApiResponse<BlogResponse> updateBlog( @PathVariable Long blogId, @RequestBody @Valid BlogUpdateRequest request) throws Throwable {
        return ApiResponse.<BlogResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Update Blog successfully")
                .result(service.updateBlog(request, blogId))
                .build();
    }

    @PatchMapping("/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change status Blog for ADMIN, MANAGER", description = "API change a status Blog ")
    public ApiResponse<Void> updateBlog(@RequestParam EnumStatus status, @PathVariable Long blogId) {
        service.changeStatus(blogId, status);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Changer Status successfully")
                .build();
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete Blog for ADMIN, MANAGER", description = "API delete Blog ")
    public ApiResponse<Blog> deleteBlog (@PathVariable Long blogId) {
        return ApiResponse.<Blog>builder()
                .code(HttpStatus.OK.value())
                .message("Delete Blog successfully")
                .result(service.deleteBlog(blogId))
                .build();
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm theo tên", description = "API Tìm kiếm theo tên Blog")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponse<BlogResponse>> searchFabric(
            @RequestParam(defaultValue = "") String key,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<BlogResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Tìm kiếm theo tên FabricName thành công")
                .result(service.searchBlogs(key, page, size))
                .build();
    }
}

