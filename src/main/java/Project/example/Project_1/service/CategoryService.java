package Project.example.Project_1.service;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.CategoryRepository;
import Project.example.Project_1.request.CategoryCreateRequest;
import Project.example.Project_1.response.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private void validateCategory(CategoryCreateRequest request) {
        if (request.getCategoryName() == null || request.getCategoryName().isEmpty() || request.getCategoryName().isBlank()) {
            throw new AppException(ErrorCode.INVALID_CATEGORY_NAME);
        }

        if (request.getDescription() == null || request.getDescription().isEmpty() || request.getDescription().isBlank()) {
            throw new AppException(ErrorCode.INVALID_CATEGORY_DESCRIPTION);
        }

        if (containsSpecialCharacter(request.getCategoryName())) {
            throw new AppException(ErrorCode.INVALID_CATEGORY_NAME);
        }
    }

    public boolean containsSpecialCharacter(String input) {
        String specialCharPattern = "[^a-zA-Z0-9_\\s]";
        return input != null && input.matches(".*" + specialCharPattern + ".*");
    }

    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        try {
            validateCategory(request);

            Category category = modelMapper.map(request, Category.class);
            category.setStatus(EnumStatus.ACTIVE);
            category.setIsDeleted(false);

            Category savedCategory = categoryRepository.save(category);
            return modelMapper.map(savedCategory, CategoryResponse.class);
        } catch (Exception e) {
            if (e instanceof AppException) {
                throw e;
            } else {
                throw new AppException(ErrorCode.ERROR_SAVE_CATEGORY);
            }
        }
    }

    public CategoryResponse updateCategory(Long categoryId, CategoryCreateRequest request) {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        validateCategory(request);

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryResponse.class);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_HAS_PRODUCTS);
        }

        category.setIsDeleted(true);
        category.setStatus(EnumStatus.DELETED);
        categoryRepository.save(category);
    }

    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        return modelMapper.map(category, CategoryResponse.class);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .filter(category -> !category.getIsDeleted())
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    public Page<CategoryResponse> getAllCategoriesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.map(category -> modelMapper.map(category, CategoryResponse.class));
    }
}