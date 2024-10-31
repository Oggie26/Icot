package Project.example.Project_1.service;

import Project.example.Project_1.enity.Product;
import Project.example.Project_1.repository.ProductRepository;
import Project.example.Project_1.request.ProductRequest;
import Project.example.Project_1.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product disableProduct(long id){
        Product product = productRepository.findProductById(id);
        product.setStatus(!product.getStatus());
        productRepository.save(product);
        return product;
    }

    @Transactional
    public ProductResponse addNewProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setStatus(true);
        product.setImage(productRequest.getImage());
        product.setPurchases(0);
        product.setDescription(productRequest.getDescription());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProduct(product);
        productRepository.save(product);
        return productResponse;
    }

    @Transactional
    public ProductResponse updateProduct(ProductRequest productRequest, long id){
        Product product = productRepository.findProductById(id);
        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setStatus(productRequest.getStatus());
        product.setImage(productRequest.getImage());
        product.setPurchases(productRequest.getPurchases());
        product.setDescription(productRequest.getDescription());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProduct(product);
        productRepository.save(product);
        return productResponse;
    }
}
