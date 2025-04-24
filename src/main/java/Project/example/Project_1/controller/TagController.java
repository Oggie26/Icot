package Project.example.Project_1.controller;

import Project.example.Project_1.request.TagCreationRequest;
import Project.example.Project_1.request.TagUpdateRequest;
import Project.example.Project_1.response.TagResponse;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Tag(name = "Tag Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class TagController {

    private final TagService tagService;

    @PostMapping
    @Operation(summary = "Thêm mới tag", description = "API Thêm mới tag")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TagResponse> createTag(@RequestBody TagCreationRequest tagRequest) {
        TagResponse response = tagService.createTag(tagRequest);
        return ApiResponse.<TagResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm mới tag thành công")
                .result(response)
                .build();
    }

    @PutMapping("/{tagId}")
    @Operation(summary = "Cập nhật tag", description = "API Cập nhật tag")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TagResponse> updateTag(@PathVariable Long tagId, @RequestBody TagUpdateRequest tagRequest) {
        TagResponse response = tagService.update(tagRequest, tagId);
        return ApiResponse.<TagResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật tag thành công")
                .result(response)
                .build();
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "Xóa tag", description = "API Xóa tag (mềm)")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xóa tag thành công")
                .build();
    }

    @GetMapping("/{tagId}")
    @Operation(summary = "Xem chi tiết tag", description = "API lấy thông tin chi tiết tag theo ID")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TagResponse> getTag(@PathVariable Long tagId) {
        TagResponse response = tagService.getTag(tagId);
        return ApiResponse.<TagResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin tag thành công")
                .result(response)
                .build();
    }

    @GetMapping
    @Operation(summary = "Xem danh sách tag", description = "API lấy danh sách tất cả tag đang hoạt động")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<TagResponse>> getAllTags() {
        List<TagResponse> tagList = tagService.getTagList().stream()
                .map(tag -> TagResponse.builder()
                        .name(tag.getName())
                        .description(tag.getDescription())
                        .status(tag.getStatus())
                        .build())
                .toList();

        return ApiResponse.<List<TagResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách tag thành công")
                .result(tagList)
                .build();
    }
}
