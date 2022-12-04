package ru.pb.market.converters;

import org.springframework.stereotype.Component;
import ru.pb.market.data.Order;
import ru.pb.market.data.OrderProduct;
import ru.pb.market.data.Product;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.dto.ProductInCartDto;

import java.util.stream.Collectors;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto p){
        return new Product(p.getTitle(), p.getPrice(), p.isActive());
    }

    public Product dtoToEntity(ProductInCartDto p){
        return new Product(p.getTitle(), p.getPrice(), true);
    }
    public ProductDto entityToDto (Product p){
        return new ProductDto(p.getId(), p.getTitle(), p.getPrice(), p.isActive());
    }

    public ProductInCartDto entityToDtoInCart(Product p){
        return new ProductInCartDto(p.getId(), p.getTitle(), p.getPrice());
    }
    public ProductInCartDto entityToDtoInCart(Product p, int count){
        return new ProductInCartDto(p.getId(), p.getTitle(), p.getPrice(), count);
    }



    public ProductInCartDto orderProductToDtoInCart(OrderProduct p, String titleById) {
        return new ProductInCartDto(p.getId(), titleById, p.getPrice(), p.getCount());
    }

    public OrderProduct productInCartDtoToOrderProduct(ProductInCartDto product, Product p, Order order) {
        return new OrderProduct(product.getPrice(), product.getCount(), p, order);
    }
}
