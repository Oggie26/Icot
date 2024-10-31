package Project.example.Project_1.service;

import Project.example.Project_1.enity.Topping;
import Project.example.Project_1.repository.ToppingRepository;
import Project.example.Project_1.request.ToppingRequest;
import Project.example.Project_1.response.ToppingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ToppingService {

    @Autowired
    ToppingRepository toppingRepository;

    public Topping disableTopping(long id){
        Topping topping = toppingRepository.findToppingById(id);
        topping.setStatus(!topping.getStatus());
        return toppingRepository.save(topping);
    }

    @Transactional
    public ToppingResponse addNewTopping(ToppingRequest toppingRequest){
        Topping topping = new Topping();
        topping.setName(toppingRequest.getName());
        topping.setPrice(toppingRequest.getPrice());
        topping.setQuantity(toppingRequest.getQuantity());
        topping.setStatus(true);
        ToppingResponse toppingResponse = new ToppingResponse();
        toppingResponse.setTopping(topping);
        toppingRepository.save(topping);
        return toppingResponse;
    }

    @Transactional
    public ToppingResponse updateTopping(ToppingRequest toppingRequest, long id){
        Topping topping = toppingRepository.findToppingById(id);
        topping.setName(toppingRequest.getName());
        topping.setPrice(toppingRequest.getPrice());
        topping.setQuantity(toppingRequest.getQuantity());
        topping.setStatus(toppingRequest.getStatus());
        ToppingResponse toppingResponse = new ToppingResponse();
        toppingResponse.setTopping(topping);
        toppingRepository.save(topping);
        return toppingResponse;
    }

}
