package Project.example.Project_1.service;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enity.Voucher;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.repository.VoucherRepository;
import Project.example.Project_1.request.VoucherCreationRequest;
import Project.example.Project_1.request.VoucherUpdateRequest;
import Project.example.Project_1.response.VoucherResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherService {

    VoucherRepository voucherRepository;
    UserRepository userRepository;

    @Transactional
    public VoucherResponse createVoucher(VoucherCreationRequest request) {
        Voucher voucher = Voucher.builder()
                .code(request.getCode())
                .point(request.getPoint())
                .discount(request.getDiscount())
                .description(request.getDescription())
                .minOrderValue(request.getMinOrderValue())
                .discountType(request.getDiscountType())
                .status(EnumStatus.ACTIVE)
                .build();
        voucherRepository.save(voucher);
        return toVoucherResponse(voucher);
    }

    @Transactional
    public VoucherResponse updateVoucher(Long voucherId, VoucherUpdateRequest request) {
        Voucher voucher = voucherRepository.findByIdAndIsDeletedFalse(voucherId)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND, "Thiếu ID của Design"));

        voucherRepository.findByCodeAndIsDeletedFalse((request.getCode()))
                .filter(existing -> !existing.getId().equals(voucherId))
                .ifPresent(existing -> {
                    throw new AppException(ErrorCode.VOUCHER_CODE_ALREADY_EXISTS, "Thiếu ID của Design");
                });

        // Cập nhật thông tin
        voucher.setCode(request.getCode());
        voucher.setPoint(request.getPoint());
        voucher.setDiscount(request.getDiscount());
        voucher.setDescription(request.getDescription());
        voucher.setMinOrderValue(request.getMinOrderValue());
        voucher.setDiscountType(request.getDiscountType());
        voucher.setStatus(request.getStatus() != null ? request.getStatus() : EnumStatus.ACTIVE);
        // Lưu lại
        voucherRepository.save(voucher);

        return toVoucherResponse(voucher);
    }


    @Transactional
    public void deleteVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findByIdAndIsDeletedFalse(voucherId).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND, "Thiếu ID của Design"));
        voucherRepository.delete(voucher);
    }

    @Transactional
    public void exchangeVoucher(Long voucherId) {
        User user = getAuthenticatedUser();
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND, "Thiếu ID của Design"));
        user.getVouchers().forEach(v -> {
            if (v.getId().equals(voucher.getId())) {
                throw new AppException(ErrorCode.INVALID_EXCHANGE_VOUCHER, "Thiếu ID của Design");
            }
        });
        if (user.getPoint() < voucher.getPoint()) {
            throw new AppException(ErrorCode.NOT_ENOUGH_POINT, "Thiếu ID của Design");
        }
        user.setPoint(user.getPoint() - voucher.getPoint());
        user.addVoucher(voucher);
        userRepository.save(user);
    }

    public VoucherResponse getVoucher(Long id) {
        return toVoucherResponse(voucherRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND, "Thiếu ID của Design")));
    }

    private VoucherResponse toVoucherResponse(Voucher voucher){
        return VoucherResponse.builder()
                .id(voucher.getId())
                .code(voucher.getCode())
                .discount(voucher.getDiscount())
                .discountType(voucher.getDiscountType())
                .minOrderValue(voucher.getMinOrderValue())
                .description(voucher.getDescription())
                .point(voucher.getPoint())
                .build();
    }
    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrThrow(username);
    }

    public List<VoucherResponse> getAvailableVouchersForCustomer() {
        User user = getAuthenticatedUser();

        return voucherRepository.findAll().stream()
                .filter(voucher -> voucher.getStatus() == EnumStatus.ACTIVE)                // Trạng thái ACTIVE
                .filter(voucher -> voucher.getPoint() <= user.getPoint())                   // Đủ điểm
                .filter(voucher -> !user.getVouchers().contains(voucher))                  // Chưa sở hữu
                .filter(voucher -> Boolean.FALSE.equals(voucher.getIsDeleted()))           // Chưa bị xóa
                .map(this::toVoucherResponse)
                .toList();
    }
}

