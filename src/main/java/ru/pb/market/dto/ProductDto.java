package ru.pb.market.dto;

import lombok.*;

@Getter
public class ProductDto {
    private Long id;
    private String title;
    private int price;

    public ProductDto(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
