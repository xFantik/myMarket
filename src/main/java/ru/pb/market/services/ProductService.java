package ru.pb.market.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.market.repositories.ProductRepository;
import ru.pb.market.dto.Product;

import java.util.List;

@Component
public class ProductService {

    //@Autowired - не лучший способ
    private ProductRepository productRepository;



    //Вместо @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String getTitleById(long id) {
        return productRepository.getById(id).getTitle();
    }

    public Product getProduct(long id){
        return productRepository.getById(id);
    }
//    @Autowired
//    public void setRepository(ProductRepositoryInMemory inMemoryRepository) {
//        this.inMemoryRepository = inMemoryRepository;
//    }

    public List<Product> getAllProducts(){
        return productRepository.findAllByPriceBetween(15, 200);
        //return repository.findAll();
    }

    public List<Product> findAllByPriceBetween(int priceStart, int priceEnd){
        return productRepository.findAllByPriceBetween(priceStart, priceEnd);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Доступные товары:\n");
        Product p;
        List<Product> products = productRepository.findAll();
        for (int i = 0; i < products.size(); i++) {
            p = products.get(i);
            sb.append(p.getId()).append(" ").append(p.getTitle()).append(", ");
        }
        sb.append("\n");

        return sb.toString();
    }

    public boolean addProduct(String title, int price)
    {
        if (title.equals("") || price == 0)
            return false;
        else {
            productRepository.save(new Product(title, price));
            return true;
        }
    }

    @Transactional  //На протяжении всего метода транзакция не закрывается.
    public void changePrice(Long productId, Integer price){
        Product product = productRepository.findById(productId).orElseThrow();
        product.setPrice(price);
        //repository.save(product);  метод не нужен, когда стоит аннотация Транзакционности

    }


    @Transactional
    public void deleteProduct(Long productId) {
        Product p = productRepository.getById(productId);
        productRepository.delete(p);
    }
}
