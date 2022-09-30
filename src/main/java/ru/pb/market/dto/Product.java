package ru.pb.market.dto;

import lombok.Getter;
import lombok.Setter;

public class Product {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private int price;

    public Product() {
    }

    public Product(long id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }



}

