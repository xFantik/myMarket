package ru.pb.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LastServiceStatisticDto {
    String name;
    Long duration;
    String username;
}
