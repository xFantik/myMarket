package ru.pb.market.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pb.market.data.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {


}
