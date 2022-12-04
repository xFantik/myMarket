package ru.pb.market.data;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "orders_products")
@NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    int price;

    @Column(name = "count")
    int count;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product;

    public OrderProduct(Integer price, Integer count, Product product, Order order) {
        this.price = price;
        this.count = count;
        this.product = product;
        this.order = order;
    }
}
