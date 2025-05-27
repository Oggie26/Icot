package Project.example.Project_1.controller;

import Project.example.Project_1.request.AddressCreationRequest;
import Project.example.Project_1.request.AddressUpdateRequest;
import Project.example.Project_1.response.AddressResponse;
import Project.example.Project_1.response.ApiResponse;
import Project.example.Project_1.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "Address Controller")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class AddressController {

    private final AddressService addAddressService;

    @PostMapping
    @Operation(summary = "Thêm mới địa chỉ", description = "API Thêm mới địa chỉ")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> addAddress(@RequestBody AddressCreationRequest addressDTO) {
        addAddressService.addAddress(addressDTO);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm mới địa chỉ thành công")
                .build();
    }

    @PutMapping("/{addressId}")
    @Operation(summary = "Cập nhật địa chỉ", description = "API Cập nhật địa chỉ")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateAddress(@PathVariable Long addressId, @RequestBody AddressUpdateRequest addressDTO) {
        addAddressService.updateAddress(addressId, addressDTO);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật địa chỉ thành công")
                .build();
    }

    @DeleteMapping("/{addressId}")
    @Operation(summary = "Xóa địa chỉ", description = "API Xóa địa chỉ")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteAddress(@PathVariable Long addressId) {
        addAddressService.deleteAddress(addressId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xóa địa chỉ thành công")
                .build();
    }

    @GetMapping
    @Operation(summary = "Xem danh sách địa chỉ", description = "API Xem danh sách địa chỉ")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<AddressResponse>> getAllAddresses() {
        List<AddressResponse> addresses = addAddressService.getAllAddresses();
        return ApiResponse.<List<AddressResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Xem danh sách địa chỉ thành công")
                .result(addresses)
                .build();
    }

    @PutMapping("/default/{addressId}")
    @Operation(summary = "Cập nhật địa chỉ mặc định", description = "API Cập nhật địa chỉ mặc định")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AddressResponse> setDefaultAddress(@PathVariable Long addressId) {
        AddressResponse addressDTO = addAddressService.setDefaultAddress(addressId);
        return ApiResponse.<AddressResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật địa chỉ mặc định thành công")
                .result(addressDTO)
                .build();
    }
}
