package ru.pb.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.pb.market.converters.ProductConverter;
import ru.pb.market.dto.ProductInCartDto;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
@Scope("singleton") // по-умолчанию
//@Scope("session") // ?
//@Scope("globalsession") // ?
//@Scope("prototype")
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private HashMap<Long, Integer> products;

    private final ProductService productService;

    private final ProductConverter productConverter;


    @PostConstruct
    public void init() {
        products = new HashMap<>();
    }

    public void addProduct(long productId, int count) {
        log.info("Добавили в корзину {} {}", count, productService.getTitleById(productId));
        products.put(productId, products.getOrDefault(productId, 0) + count);
    }


    public void removeProduct(long productId, int count) {
        products.put(productId, products.getOrDefault(productId, 0) - count);
        log.info("Убрали из корзины {} {}", count, productService.getTitleById(productId));
        if (products.get(productId) <= 0) {
            products.remove(productId);
            log.info("В корзине не осталось {}, убрали совсем",productService.getTitleById(productId) );
        }
    }

    public List<ProductInCartDto> get() {

        Long[] ids= new Long[products.size()];
        products.keySet().toArray(ids);

        List<ProductInCartDto> productsByIdIn = productService.getProductsByIdIn(ids).stream()
                        .map(product -> productConverter.entityToDtoInCart(product, products.get(product.getId())))
                        .toList();


        if (products.size() > productsByIdIn.size()){
            products.entrySet().removeIf(e -> !isPresentInList(productsByIdIn, e.getKey()));
        }

        return productsByIdIn;
    }

    private boolean isPresentInList(List<ProductInCartDto> list, Long id){
        for (ProductInCartDto product : list) {
            if (product.getId().equals(id))
                return true;
        }
        log.warn("Продукт с id {}, не найден в базе, удаляем из корзины", id);
        return false;
    }


}
