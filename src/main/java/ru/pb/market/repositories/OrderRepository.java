package ru.pb.market.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pb.market.data.Order;
import ru.pb.market.data.User;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> getOrderByOwnerIs(User u);

}
