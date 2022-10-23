package ru.pb.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.pb.market.data.Product;
@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private String title;
    private int price;
    public ProductDto(Product p) {
        this.title = p.getTitle();
        this.price = p.getPrice();
    }

}
