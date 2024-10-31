package Project.example.Project_1.api;

import Project.example.Project_1.enity.Category;
import Project.example.Project_1.repository.CategoryRepository;
import Project.example.Project_1.request.CategoryRequest;
import Project.example.Project_1.response.CategoryResponse;
import Project.example.Project_1.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class CategoryAPI {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> list = categoryRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("addNewCategory")
    public ResponseEntity<CategoryResponse> addNewCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryResponse categoryResponse = categoryService.addNewCategory(categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("updateCategory/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable long id){
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryRequest, id);
        return ResponseEntity.ok(categoryResponse);
    }

    @PatchMapping("disableCategory/{id}")
    public ResponseEntity<Category> disableCategory(@PathVariable long id){
        Category category = categoryService.disableCategory(id);
        return ResponseEntity.ok(category);
    }

}
