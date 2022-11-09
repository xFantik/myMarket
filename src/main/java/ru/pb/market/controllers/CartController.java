package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.pb.market.data.Product;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.dto.ProductInCartDto;
import ru.pb.market.services.CartService;
import ru.pb.market.services.ProductService;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping("")
    public List<ProductInCartDto> getCart(@RequestParam(defaultValue = "1") Integer page) {
        return cartService.get();
    }

    @DeleteMapping("")
    public void deleteProduct(@RequestParam Long productId, @RequestParam(defaultValue = "1") int count) {
        cartService.removeProduct(productId, count);
    }


    @PostMapping("")
    public void addProduct(@RequestParam Long productId, @RequestParam(defaultValue = "1") int count) {
        cartService.addProduct(productId, count);
    }

}
