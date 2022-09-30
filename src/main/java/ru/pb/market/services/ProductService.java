package ru.pb.market.services;

import org.springframework.stereotype.Component;
import ru.pb.market.repositories.ProductRepository;
import ru.pb.market.dto.Product;

import java.util.List;

@Component
public class ProductService {

    //@Autowired - не лучший способ
    private ProductRepository repository;


    //Вместо @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public String getTitleById(long id) {
        return repository.findById(id).getTitle();
    }

    public Product getProduct(long id){
        return repository.findById(id);
    }
//    @Autowired
//    public void setRepository(ProductRepository inMemoryRepository) {
//        this.inMemoryRepository = inMemoryRepository;
//    }

    public List<Product> getAllProducts(){
        return repository.getProducts();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Доступные товары:\n");
        Product p;
        for (int i = 0; i < repository.getProducts().size(); i++) {
            p = repository.getProducts().get(i);
            sb.append(p.getId()).append(" ").append(p.getTitle()).append(", ");
        }
        sb.append("\n");

        return sb.toString();
    }

}
