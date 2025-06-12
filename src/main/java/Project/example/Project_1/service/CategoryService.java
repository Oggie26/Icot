package Project.example.Project_1.service;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.CategoryRepository;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.CategoryRequest;
import Project.example.Project_1.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    public CategoryResponse createCategory(CategoryRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if(categoryRepository.findByCategoryNameAndIsDeletedFalse(request.getCategoryName()).isPresent()){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .status(EnumStatus.ACTIVE)
                .description(request.getDescription())
                .build();
        category.setIsDeleted(false);
        categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .status(category.getStatus())
                .description(category.getDescription())
                .build();
    }

    public CategoryResponse updateCategory(CategoryRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Category category = categoryRepository.findByIdAndIsDeletedFalse(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if(categoryRepository.findByCategoryNameAndIsDeletedFalse(request.getCategoryName()).isPresent()){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .status(category.getStatus())
                .description(category.getDescription())
                .build();
    }

    public void disableCategory(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (category.getStatus().equals(EnumStatus.ACTIVE)){
            category.setStatus(EnumStatus.INACTIVE);
        }else{
            category.setStatus(EnumStatus.ACTIVE);
        }
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setIsDeleted(true);
        category.setStatus(EnumStatus.INACTIVE);
        categoryRepository.save(category);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll()
                .stream()
                .filter(category -> !category.getIsDeleted())
                .toList();
    }

    public CategoryResponse getById(Long id){
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryResponse.builder()
                .id(category.getId())
                .description(category.getDescription())
                .categoryName(category.getCategoryName())
                .status(category.getStatus())
                .build();
    }

}
