package Project.example.Project_1.service;

import Project.example.Project_1.enity.Product;
import Project.example.Project_1.enity.Size;
import Project.example.Project_1.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeService {

    @Autowired
    SizeRepository sizeRepository;

    public List<Size> getSizeByProduct(String productId) {
        List<Size> list = new ArrayList<>();
        list =  sizeRepository.findAllByProductId(productId)
                .stream()
                .toList();

        return list;
    }

    
}
