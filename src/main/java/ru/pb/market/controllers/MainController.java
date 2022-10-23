package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.pb.market.data.Product;
import ru.pb.market.services.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {


    private final ProductService productService;
    //http://localhost:8189/



    ///products/4/info
    @GetMapping("/product/{id}/info")
    public String info(Model model, @PathVariable int id) {
        model.addAttribute("product", productService.getProduct(id));
        return "productInfo.html";                      //thymeleaf example
    }



    @GetMapping("/page")
    public String page(Model model) {
        model.addAttribute("productList", productService.getAllProducts());
        return "productList.html";                  //thymeleaf example
    }




}
