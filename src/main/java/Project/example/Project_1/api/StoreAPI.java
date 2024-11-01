package Project.example.Project_1.api;

import Project.example.Project_1.enity.Store;
import Project.example.Project_1.repository.StoreRepository;
import Project.example.Project_1.request.StoreRequest;
import Project.example.Project_1.response.StoreResponse;
import Project.example.Project_1.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
public class StoreAPI {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreService storeService;

    @GetMapping("getAllStories")
    public ResponseEntity<List<Store>> getAllStories(){
        List<Store> list = storeRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("addNewStore")
    public ResponseEntity<StoreResponse> addNewStore(@RequestBody StoreRequest storeRequest){
        StoreResponse storeResponse = storeService.addNewStore(storeRequest);
        return ResponseEntity.ok(storeResponse);
    }

    @PutMapping("updateStore/{id}")
    public ResponseEntity<StoreResponse> updateStore(@RequestBody StoreRequest storeRequest, @PathVariable long id){
        StoreResponse storeResponse = storeService.updateStore(storeRequest, id);
        return ResponseEntity.ok(storeResponse);
    }

    @PatchMapping("disableStore/{id}")
    public ResponseEntity<Store> disableStore(@PathVariable long id){
        Store store = storeService.disableStore(id);
        return ResponseEntity.ok(store);
    }
}
