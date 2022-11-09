package ru.pb.market.converters;

import org.springframework.stereotype.Component;
import ru.pb.market.data.Product;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.dto.ProductInCartDto;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto p){
        return new Product(p.getTitle(), p.getPrice());
    }
    public ProductDto entityToDto (Product p){
        return new ProductDto(p.getTitle(), p.getPrice());
    }

    public ProductInCartDto entityToDtoInCart(Product p){
        return new ProductInCartDto(p.getId(), p.getTitle(), p.getPrice());
    }
}
