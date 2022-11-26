package ru.pb.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import ru.pb.market.data.Product;
import ru.pb.market.data.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private Integer totalCost;

    private User owner;

    private LocalDateTime createdAt;

}

