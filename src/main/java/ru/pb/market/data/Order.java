package ru.pb.market.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User owner;

    @Column(name = "total_cost")
    private Integer totalCost;


    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;



    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

