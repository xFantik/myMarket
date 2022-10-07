package ru.pb.market.repositories;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.pb.market.dto.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductRepository {
    @Getter
    private List<Product> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>(Arrays.asList(
                new Product(1l, "Bread", 50),
                new Product(2l, "Milk", 80),
                new Product(3l, "Orange", 100),
                new Product(4l, "Potato", 15),
                new Product(5l, "Water", 10)
        ));
    }

    public Product findById(long id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElseThrow();
    }


    public boolean addProduct(long id, String title, int price) {
        if (products.stream().anyMatch(p -> p.getId() == id))
            return false;
        else
            products.add(new Product(id, title, price));
        return true;
    }


    public void deleteProduct(Long productId) {
        products.remove(findById(productId));
    }
}
