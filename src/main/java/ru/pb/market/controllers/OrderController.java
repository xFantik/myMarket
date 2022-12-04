package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pb.market.dto.OrderDto;
import ru.pb.market.dto.ProductInCartDto;
import ru.pb.market.services.CartService;
import ru.pb.market.services.OrderService;
import ru.pb.market.utils.JwtTokenUtil;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("")
    public List<OrderDto> getOrders(@RequestHeader Map<String, String> headers) {
        return orderService.getOrders(getUserNameFromHeaders(headers));
    }


    @GetMapping("/{orderId}")
    public List<ProductInCartDto> getProducts(@PathVariable Long orderId) {
        return orderService.get(orderId);
    }

    @PostMapping("/create")
    public Long createOrder(@RequestHeader Map<String, String> headers) {
        return orderService.createOrder(getUserNameFromHeaders(headers));
    }


    private String getUserNameFromHeaders(Map<String, String> headers){
        return  jwtTokenUtil.getUsernameFromToken(headers.get("authorization").substring(7));
    }

}
