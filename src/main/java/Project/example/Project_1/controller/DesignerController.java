package Project.example.Project_1.controller;

import Project.example.Project_1.enity.Design;
import Project.example.Project_1.request.DesignerRequest;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.DesignerResponse;
import Project.example.Project_1.service.DesignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/design")
@RequiredArgsConstructor
@Tag(name = "Design Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class DesignerController {

    private final DesignService designService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new design")
    public ApiResponse<DesignerResponse> createDesign(@RequestBody DesignerRequest request) {
        return ApiResponse.<DesignerResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Design created successfully")
                .result(designService.createDesigner(request))
                .build();
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update design")
    public ApiResponse<DesignerResponse> updateDesign(@RequestBody DesignerRequest request) {
        return ApiResponse.<DesignerResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Design updated successfully")
                .result(designService.updateDesigner(request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft delete design by id")
    public ApiResponse<Void> deleteDesign(@PathVariable Long id) {
        designService.deleteDesigner(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Design deleted successfully")
                .build();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all designs")
    public ApiResponse<List<Design>> getAllDesigns() {
        return ApiResponse.<List<Design>>builder()
                .code(HttpStatus.OK.value())
                .message("Get all designs successfully")
                .result(designService.getAllDesigners())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get design by ID")
    public ApiResponse<Design> getDesignById(@PathVariable Long id) {
        return ApiResponse.<Design>builder()
                .code(HttpStatus.OK.value())
                .message("Get design successfully")
                .result(designService.getDesigner(id))
                .build();
    }
}
