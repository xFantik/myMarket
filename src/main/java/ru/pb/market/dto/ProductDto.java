package ru.pb.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.pb.market.data.Product;
@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private int price;

    public ProductDto(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
