package ru.pb.market.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "products")
public class Product {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Column(name = "title")
    private String title;

    @Getter
    @Setter
    @Column(name = "price")
    private Integer price;

    public Product() {
    }

    public Product(long id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }


    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }
}

