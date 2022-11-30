package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.pb.market.dto.ProductInCartDto;
import ru.pb.market.services.CartService;
import ru.pb.market.utils.JwtTokenUtil;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("")
    public List<ProductInCartDto> getCart(@RequestHeader Map<String, String> headers) {
        //todo: заменить на выдачу имя из контекста))
        System.out.println("имя: "+SecurityContextHolder.getContext().getAuthentication().getName());
        return cartService.get(getUserNameFromHeaders(headers));
    }

    @DeleteMapping("")
    public void deleteProduct(@RequestHeader Map<String, String> headers, @RequestParam Long productId, @RequestParam(defaultValue = "1") int count) {
        cartService.removeProduct(getUserNameFromHeaders(headers), productId, count);
    }


    @PostMapping("")
    public void addProduct(@RequestHeader Map<String, String> headers, @RequestParam Long productId, @RequestParam(defaultValue = "1") int count) {
        cartService.addProduct(getUserNameFromHeaders(headers), productId, count);
    }

    private String getUserNameFromHeaders(Map<String, String> headers) {

        return jwtTokenUtil.getUsernameFromToken(headers.get("authorization").substring(7));
    }

}
