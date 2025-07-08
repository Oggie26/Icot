package Project.example.Project_1.controller;

import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/total-revenue")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get total revenue", description = "Tính tổng tiền từ Order + BookOrder")
    public ApiResponse<Double> getTotalRevenue() {
        return ApiResponse.<Double>builder()
                .code(HttpStatus.OK.value())
                .message("Get total revenue successfully")
                .result(dashboardService.getTotalRevenue())
                .build();
    }

    @GetMapping("/total-orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get total number of orders", description = "Đếm tổng số đơn hàng từ Order + BookOrder")
    public ApiResponse<Long> getTotalOrders() {
        return ApiResponse.<Long>builder()
                .code(HttpStatus.OK.value())
                .message("Get total orders successfully")
                .result(dashboardService.getTotalOrderCount())
                .build();
    }

    @GetMapping("/total-users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get total number of user", description = "Đếm tổng số customer")
    public ApiResponse<Long> getTotalUsers() {
        return ApiResponse.<Long>builder()
                .code(HttpStatus.OK.value())
                .message("Get total user successfully")
                .result(dashboardService.getTotalUserCount())
                .build();
    }

}
