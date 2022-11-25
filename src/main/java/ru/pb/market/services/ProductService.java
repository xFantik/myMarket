package ru.pb.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.pb.market.converters.ProductConverter;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.exceptions.ResourceNotFoundException;
import ru.pb.market.repositories.ProductRepository;
import ru.pb.market.data.Product;
import ru.pb.market.repositories.specification.ProductSpecification;
import ru.pb.market.validators.ProductValidator;

import java.util.List;
@Component
@RequiredArgsConstructor //конструктор для Autowired
@Slf4j
public class ProductService {

    //@Autowired - не лучший способ

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    private final ProductValidator productValidator;


    //Вместо @Autowired
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

//    @Autowired
//    public void setRepository(ProductRepositoryInMemory inMemoryRepository) {
//        this.inMemoryRepository = inMemoryRepository;
//    }


    public String getTitleById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found")).getTitle();
    }

    public Product getProduct(long id) {
        //productRepository.findById(id).map(s -> productConverter.entityToDto(s)).orElseThrow();
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    public Page<ProductDto> find(Integer page, Integer minPrice, Integer maxPrice, String partName, boolean isActive) {
        Specification<Product> specification = Specification.where(null);

        if (minPrice != null)
            specification = specification.and(ProductSpecification.priceGreaterOrEquals(minPrice));

        if (maxPrice != null)
            specification = specification.and(ProductSpecification.priceLessOrEquals(maxPrice));

        if (partName != null)
            specification = specification.and(ProductSpecification.nameLike(partName));

        if (isActive)
            specification = specification.and(ProductSpecification.isActive());

        if (page < 1) {
            page = 1;
        }

        return productRepository.findAll(specification, PageRequest.of(page - 1, 10)).map((product)->productConverter.entityToDto(product));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Доступные товары:\n");
        Product p;
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            p = product;
            sb.append(p.getId()).append(" ").append(p.getTitle()).append(", ");
        }
        sb.append("\n");

        return sb.toString();
    }

    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(ProductDto productDto) {
        productValidator.validate(productDto);
        productRepository.save(productConverter.dtoToEntity(productDto));
        log.info("Добавлен продукт: " + productDto.getTitle());
    }

    @Transactional
    public void update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Product with id " + productDto.getId() + " not found"));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setActive(productDto.isActive());
    }
    @Transactional  //На протяжении всего метода транзакция не закрывается.
    public void changePrice(Long productId, Integer price) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setPrice(price);
        //repository.save(product);  метод не нужен, когда стоит аннотация Транзакционности

    }



    public List<Product> getProductsByIdIn(Long[] ids){
        return productRepository.getProductByIdIn(ids);
    }
}
