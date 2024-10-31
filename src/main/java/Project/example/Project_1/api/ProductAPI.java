package Project.example.Project_1.api;

import Project.example.Project_1.enity.Product;
import Project.example.Project_1.repository.ProductRepository;
import Project.example.Project_1.request.ProductRequest;
import Project.example.Project_1.response.ProductResponse;
import Project.example.Project_1.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ProductAPI {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @GetMapping("getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts (){
        List<Product> list =  productRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("addNewProduct")
    public ResponseEntity<ProductResponse> addNewProduct(@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.addNewProduct(productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("updateProduct/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable long id){
        ProductResponse productResponse = productService.updateProduct(productRequest, id);
        return ResponseEntity.ok(productResponse);
    }

    @PatchMapping("disableProduct/{id}")
    public ResponseEntity<Product> disableProduct(@PathVariable long id){
        Product product = productService.disableProduct(id);
        return ResponseEntity.ok(product);
    }
}
