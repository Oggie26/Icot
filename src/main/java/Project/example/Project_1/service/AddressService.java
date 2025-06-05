package Project.example.Project_1.service;

import Project.example.Project_1.enity.Address;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.AddressRepository;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.AddressCreationRequest;
import Project.example.Project_1.request.AddressUpdateRequest;
import Project.example.Project_1.response.AddressResponse;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    AddressRepository addressRepository;
    UserRepository userRepository;

    @Transactional
    public void addAddress(AddressCreationRequest request) {
        User user = getAuthenticatedUser();
        if (request.getIsDefault()) {
            // Lấy tất cả các địa chỉ của người dùng
            List<Address> existingAddresses = addressRepository.findByUser(user);
            // Cập nhật tất cả địa chỉ cũ thành không mặc định
            existingAddresses.forEach(address -> address.setIsDefault(false));
            addressRepository.saveAll(existingAddresses); // Lưu tất cả các thay đổi
        }
        Address address = Address.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .city(request.getCity())
                .district(request.getDistrict())
                .ward(request.getWard())
                .street(request.getStreet())
                .addressLine(request.getAddressLine())
                .isDefault(request.getIsDefault())
                .user(user)
                .build();
        addressRepository.save(address);
    }

    @Transactional
    public void updateAddress(Long addressId, AddressUpdateRequest request) {
        User user = getAuthenticatedUser();
        Address newaddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND, "Thiếu ID của Design"));
        if (request.getIsDefault()) {
            // Lấy tất cả các địa chỉ của người dùng
            List<Address> existingAddresses = addressRepository.findByUser(user);
            // Cập nhật tất cả địa chỉ cũ thành không mặc định
            existingAddresses.forEach(address -> address.setIsDefault(false));
            addressRepository.saveAll(existingAddresses); // Lưu tất cả các thay đổi
        }
        newaddress.setName(request.getName());
        newaddress.setPhone(request.getPhone());
        newaddress.setCity(request.getCity());
        newaddress.setDistrict(request.getDistrict());
        newaddress.setWard(request.getWard());
        newaddress.setStreet(request.getStreet());
        newaddress.setAddressLine(request.getAddressLine());
        newaddress.setIsDefault(request.getIsDefault());
        addressRepository.save(newaddress);
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND, "Thiếu ID của Design"));
        addressRepository.delete(address);
    }

    public List<AddressResponse> getAllAddresses() {
        User user = getAuthenticatedUser();
        List<Address> addresses = addressRepository.findByUser(user);
        return addresses.stream()
                .map(address -> new AddressResponse(address.getId(),
                        address.getName(), address.getPhone(), address.getCity(),
                        address.getDistrict(), address.getWard(), address.getStreet(),
                        address.getAddressLine(), address.getIsDefault()))
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressResponse setDefaultAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND, "Thiếu ID của Design"));
        // Set all other addresses to non-default
        addressRepository.findByUser(getAuthenticatedUser()).forEach(addr -> addr.setIsDefault(false));
        address.setIsDefault(true);
        addressRepository.save(address);
        return new AddressResponse(address.getId(), address.getName(), address.getPhone(), address.getCity(),
                address.getDistrict(), address.getWard(), address.getStreet(), address.getAddressLine(), true);
    }

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrThrow(username);
    }
}
