package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.pb.market.data.Product;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.services.ProductService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("")
    public Page<ProductDto> find(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(required = false) Integer minPrice,
                              @RequestParam(required = false) Integer maxPrice,
                              @RequestParam(required = false) String partName,
                              @RequestParam(defaultValue = "true") boolean active) {
        return productService.find(page, minPrice, maxPrice, partName, active);
    }



    @PostMapping("")
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @PutMapping("")
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.update(productDto);
    }


    @GetMapping("/{id}")
    public Product infoRest(@PathVariable int id) {
        return productService.getProduct(id);
    }


    @GetMapping("/all")
    public List<Product> get() {
        return productService.getAllProducts();

    }

}
