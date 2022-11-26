package ru.pb.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.market.converters.ProductConverter;
import ru.pb.market.data.Order;
import ru.pb.market.data.OrderProduct;
import ru.pb.market.data.User;
import ru.pb.market.dto.OrderDto;
import ru.pb.market.dto.ProductInCartDto;
import ru.pb.market.exceptions.EmptyCartException;
import ru.pb.market.repositories.OrderProductRepository;
import ru.pb.market.repositories.OrderRepository;

import java.util.List;


@Component
@Scope("singleton") // по-умолчанию
//@Scope("session") // ?
//@Scope("globalsession") // ?
//@Scope("prototype")
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ProductConverter productConverter;
    private final ProductService productService;




    @Transactional
    public List<OrderDto> getOrders(String username) {
        User u = userService.findUserByName(username).get();
        return orderRepository.getOrderByOwnerIs(u).stream().map(order -> new OrderDto(order.getId(), order.getTotalCost(), order.getOwner(), order.getCreatedAt())).toList();
    }


    @Transactional
    public List<ProductInCartDto> get(Long orderId) {
        Order o = orderRepository.getById(orderId);
        List<OrderProduct> orderProducts = o.getOrderProducts();

        List<ProductInCartDto> productsDto = orderProducts.stream()
                .map(p -> productConverter.orderProductToDtoInCart(p, productService.getTitleById(p.getId()))).toList();

        return productsDto;

    }

    @Transactional
    public Long createOrder(String userName) {
        List<ProductInCartDto> productsInCartDtoList = cartService.get(userName);
        cartService.clearCart(userName);
        if (productsInCartDtoList.size() == 0) {
            throw new EmptyCartException("Корзина пуста");
        }

        Order order = new Order();
        order.setOwner(userService.findUserByName(userName).get());
        order.setTotalCost(calculateTotalCost(productsInCartDtoList));

        List<OrderProduct> orderProductsList = productsInCartDtoList.stream().map(pro ->
                productConverter.productInCartDtoToOrderProduct(pro, productService.getProduct(pro.getId()),order)).toList();


        orderProductRepository.saveAll(orderProductsList);

        order.setOrderProducts(orderProductsList);
        orderRepository.save(order);

        return order.getId();

    }

    private Integer calculateTotalCost(List<ProductInCartDto> products) {
        int total = 0;
        for (ProductInCartDto product : products) {
            total += product.getPrice() * product.getCount();
        }
        return total;
    }
}
