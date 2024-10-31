package Project.example.Project_1.service;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.repository.CategoryRepository;
import Project.example.Project_1.request.CategoryRequest;
import Project.example.Project_1.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category disableCategory(long id){
        Category category = categoryRepository.findCategoryById(id);
        category.setStatus(!category.getStatus());
        return categoryRepository.save(category);
    }

    public CategoryResponse addNewCategory(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setPrice(categoryRequest.getPrice());
        category.setStatus(true);
        category.setSize(categoryRequest.getSize());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategory(category);
        categoryRepository.save(category);
        return categoryResponse;
    }

    public CategoryResponse updateCategory(CategoryRequest categoryRequest, long id){
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setPrice(categoryRequest.getPrice());
        category.setStatus(categoryRequest.getStatus());
        category.setSize(categoryRequest.getSize());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategory(category);
        categoryRepository.save(category);
        return categoryResponse;
    }
}
