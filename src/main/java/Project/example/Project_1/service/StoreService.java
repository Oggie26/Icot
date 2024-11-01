package Project.example.Project_1.service;

import Project.example.Project_1.enity.Store;
import Project.example.Project_1.repository.StoreRepository;
import Project.example.Project_1.request.StoreRequest;
import Project.example.Project_1.response.StoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    public Store disableStore(long id){
        Store store = storeRepository.findStoreById(id);
        store.setStatus(!store.getStatus());
        return storeRepository.save(store);
    }

    @Transactional
    public StoreResponse addNewStore(StoreRequest storeRequest){
        Store store = new Store();
        store.setStoreName(storeRequest.getStoreName());
        store.setImage(storeRequest.getImage());
        store.setAddress(storeRequest.getAddress());
        store.setRevenue(storeRequest.getRevenue());
        store.setStatus(true);
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setStore(store);
        storeRepository.save(store);
        return storeResponse;
    }

    @Transactional
    public StoreResponse updateStore(StoreRequest storeRequest, long id){
        Store store = storeRepository.findStoreById(id);
        store.setStoreName(storeRequest.getStoreName());
        store.setImage(storeRequest.getImage());
        store.setAddress(storeRequest.getAddress());
        store.setRevenue(storeRequest.getRevenue());
        store.setStatus(storeRequest.getStatus());
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setStore(store);
        storeRepository.save(store);
        return storeResponse;
    }
}
