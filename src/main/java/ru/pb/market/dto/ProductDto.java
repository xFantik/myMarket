package ru.pb.market.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private int price;
    private boolean active;

}
