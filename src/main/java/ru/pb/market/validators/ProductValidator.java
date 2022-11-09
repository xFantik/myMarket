package ru.pb.market.validators;

import org.springframework.stereotype.Component;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.exceptions.ValidateException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto){
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice()<1){
            errors.add("Цена не может быть меньше 1");
        }
        if (productDto.getTitle().isBlank())
            errors.add("Не заполнено название товара");
        if (!errors.isEmpty()){
            throw new ValidateException(errors);
        }
    }
}
