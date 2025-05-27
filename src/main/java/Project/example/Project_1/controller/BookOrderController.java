package Project.example.Project_1.controller;

import Project.example.Project_1.enity.BookOrder;
import Project.example.Project_1.enums.EnumBookOrder;
import Project.example.Project_1.request.BookOrderCreateRequest;
import Project.example.Project_1.request.BookOrderUpdateRequest;
import Project.example.Project_1.request.ChangeStatus;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.response.BookOrderResponse;
import Project.example.Project_1.response.PageResponse;
import Project.example.Project_1.service.BookOrderService;
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
@RequestMapping("/bookOrder")
@RequiredArgsConstructor
@Tag(name = "BookOrder Controller")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookOrderController {

    @Autowired
    BookOrderService bookOrderService;

    //SEARCH
    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm theo tên", description = "API Tìm kiếm theo tên FabricName")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PageResponse<BookOrderResponse>> searchFabric(
            @RequestParam(defaultValue = "") String key,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<BookOrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Tìm kiếm theo tên FabricName thành công")
                .result(bookOrderService.searchBookOrderByPhone(key, page, size))
                .build();
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Tạo BookOrder mới")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BookOrderResponse> createBookOrder(@Valid @RequestBody BookOrderCreateRequest request) {
        return ApiResponse.<BookOrderResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo BookOrder thành công")
                .result(bookOrderService.bookOrder(request))
                .build();
    }

    // UPDATE
    @PutMapping
    @Operation(summary = "Cập nhật thông tin BookOrder")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BookOrderResponse> updateBookOrder(@Valid @RequestBody BookOrderUpdateRequest request) {
        return ApiResponse.<BookOrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật BookOrder thành công")
                .result(bookOrderService.updateBookOrder(request))
                .build();
    }

    // Cancel BookOrder (soft delete)
    @DeleteMapping("/{id}")
    @Operation(summary = "Xoá BookOrder (soft delete)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFabric(@PathVariable Long id) {
        bookOrderService.cancelBookOrder(id);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết BookOrder theo ID")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BookOrderResponse> getBookOrderById(@PathVariable Long id) {
        return ApiResponse.<BookOrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy chi tiết BookOrder thành công")
                .result(bookOrderService.getBookOrderById(id))
                .build();
    }

    // GET ALL của User (trạng thái ACTIVE)
    @GetMapping("/all")
    @Operation(summary = "Lấy tất cả BookOrder đang ACTIVE")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<BookOrder>> getMyBookOrders() {
        return ApiResponse.<List<BookOrder>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy tất cả BookOrder của user thành công")
                .result(bookOrderService.getMyBookOrder())
                .build();
    }

    @PutMapping("/{bookingOrderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change a status to do Booking Order", description = "API retrieve Booking Order ")
    public ApiResponse<BookOrder> changeStatus(@RequestBody @Valid ChangeStatus status, @PathVariable Long bookOrderId) {
        return ApiResponse.<BookOrder>builder()
                .code(HttpStatus.OK.value())
                .message("Change a status successfully ")
                .result(bookOrderService.changeStatus(status, bookOrderId))
                .build();
    }

}
