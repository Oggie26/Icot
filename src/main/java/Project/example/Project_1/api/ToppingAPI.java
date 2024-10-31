package Project.example.Project_1.api;

import Project.example.Project_1.enity.Topping;
import Project.example.Project_1.repository.ToppingRepository;
import Project.example.Project_1.request.ToppingRequest;
import Project.example.Project_1.response.ToppingResponse;
import Project.example.Project_1.service.ToppingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topping")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class ToppingAPI {
    @Autowired
    ToppingRepository toppingRepository;

    @Autowired
    ToppingService toppingService;

    @GetMapping("getAllToppings")
    public ResponseEntity<List<Topping>> getAllToppings (){
        List<Topping> list = toppingRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("addNewTopping")
    public ResponseEntity<ToppingResponse> addNewTopping(@RequestBody ToppingRequest toppingRequest){
        ToppingResponse toppingResponse = toppingService.addNewTopping(toppingRequest);
        return ResponseEntity.ok(toppingResponse);
    }

    @PutMapping("updateTopping/{id}")
    public ResponseEntity<ToppingResponse> updateTopping(@RequestBody ToppingRequest toppingRequest, @PathVariable long id){
        ToppingResponse toppingResponse = toppingService.updateTopping(toppingRequest, id);
        return ResponseEntity.ok(toppingResponse);
    }

    @PatchMapping("disableTopping/{id}")
    public ResponseEntity<Topping> disableTopping(@PathVariable long id){
        Topping topping = toppingService.disableTopping(id);
        return ResponseEntity.ok(topping);
    }
}
