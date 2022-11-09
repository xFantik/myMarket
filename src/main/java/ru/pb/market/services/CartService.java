package ru.pb.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.pb.market.converters.ProductConverter;
import ru.pb.market.dto.ProductInCartDto;
import ru.pb.market.exceptions.ResourceNotFoundException;
import ru.pb.market.repositories.ProductRepository;

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

        products.entrySet().removeIf(e -> !productService.existById(e.getKey()));    //проверка на удаленные из базы продукты (надо убрать. так как удалять продукты из базы не комильфо)
        List<ProductInCartDto> result = new ArrayList<>();

        ProductInCartDto productInCartDto;
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            try {
                productInCartDto=productConverter.entityToDtoInCart(productService.getProduct((entry.getKey())));
                productInCartDto.setCount(entry.getValue());
                result.add(productInCartDto);
            } catch (ResourceNotFoundException e) {
                log.warn("Продукт в корзине с id {}, не найден в базе", entry.getKey());
            }
        }
        return result;
    }


}
