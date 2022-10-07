package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.pb.market.dto.Product;
import ru.pb.market.services.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {


    private final ProductService productService;
    //http://localhost:8189



    ///calculate?first=5&b=4
    @GetMapping("/calculate")
    @ResponseBody
    public int calculate(@RequestParam(name = "first") int a, @RequestParam(required = false, defaultValue = "0") int b){
        return a+b;
    }


    ///products/4/info
    @GetMapping("/product/{id}/info")
    public String info(Model model, @PathVariable int id){
        model.addAttribute("product", productService.getProduct(id));
        return "productInfo.html";
    }

    @GetMapping("/product/all")
    @ResponseBody
    public List<Product> get(){
//        model.addAttribute("product", productService.getProduct(id));
        return productService.getAllProducts();
    }



    @GetMapping("/page")
    public String page(Model model){
        model.addAttribute("productList", productService.getAllProducts());
        return "productList.html";
    }

    @GetMapping ("/product/addProduct")
    @ResponseBody
    public void addProduct(@RequestParam long id, @RequestParam String title, @RequestParam(defaultValue = "0") int price){
        productService.addProduct(id, title, price);

    }

    @GetMapping ("/product/deleteProduct")
    @ResponseBody
    public void addProduct(@RequestParam Long productId){
        productService.deleteProduct(productId);
    }



    @GetMapping ("/form")
    public String form(){
        return "add_form";
    }
}
