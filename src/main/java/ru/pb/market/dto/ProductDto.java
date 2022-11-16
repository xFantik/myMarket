package ru.pb.market.dto;

import lombok.*;

@Getter
public class ProductDto {
    private Long id;
    private String title;
    private int price;

    private boolean active;

    public ProductDto(String title, int price, boolean isActive) {
        this.title = title;
        this.price = price;
        this.active = isActive;
    }
}
