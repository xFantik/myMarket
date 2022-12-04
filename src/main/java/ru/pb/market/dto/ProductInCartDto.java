package ru.pb.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductInCartDto {
    private Long id;
    private String title;
    private int price;

    private int count;


    public ProductInCartDto(Long id, String title, Integer price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }


}

