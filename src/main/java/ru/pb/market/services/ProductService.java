package ru.pb.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.exceptions.AddProductException;
import ru.pb.market.exceptions.ResourceNotFoundException;
import ru.pb.market.repositories.ProductRepository;
import ru.pb.market.data.Product;
import ru.pb.market.repositories.specification.ProductSpecification;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ProductService {

    //@Autowired - не лучший способ

    private ProductRepository productRepository;


    //Вместо @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @Autowired
//    public void setRepository(ProductRepositoryInMemory inMemoryRepository) {
//        this.inMemoryRepository = inMemoryRepository;
//    }


    public String getTitleById(long id) {
        return productRepository.getById(id).getTitle();
    }

    public Product getProduct(long id) {

//        productRepository.findById(id).map(s -> new ProductDto(s)).orElseThrow();   //для использования dto
        ProductDto p = productRepository.findById(id).map((s -> new ProductDto(s))).get();
        if (p == null)
            throw new NoSuchElementException("No value present");

        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

    }


    public List<Product> getAllProducts() {
        return productRepository.findAllByPriceBetween(15, 200);
        //return repository.findAll();
    }


    public Page<Product> find(Integer page, Integer minPrice, Integer maxPrice, String partName) {
        Specification<Product> specification = Specification.where(null);

        if (minPrice != null)
            specification = specification.and(ProductSpecification.priceGreaterOrEquals(minPrice));

        if (maxPrice != null)
            specification = specification.and(ProductSpecification.priceLessOrEquals(maxPrice));

        if (partName != null)
            specification = specification.and(ProductSpecification.nameLike(partName));

        if (page < 1) {
            page = 1;
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Доступные товары:\n");
        Product p;
        List<Product> products = productRepository.findAll();
        for (int i = 0; i < products.size(); i++) {
            p = products.get(i);
            sb.append(p.getId()).append(" ").append(p.getTitle()).append(", ");
        }
        sb.append("\n");

        return sb.toString();
    }

    public void addProduct(String title, int price) {
        if (title.equals(""))
            throw new AddProductException("Не заполнено название продукта!");
        if (productRepository.getProductByTitleIs(title).isPresent()) {
            throw new AddProductException("Данный продукт существует");
        } else {
            productRepository.save(new Product(title, price));
        }
    }

    @Transactional  //На протяжении всего метода транзакция не закрывается.
    public void changePrice(Long productId, Integer price) {
//        Product product = productRepository.findById(productId).orElseThrow();
        Product product = productRepository.findById(productId).get();

        if (product == null)
            throw new NoSuchElementException("No value present");
        product.setPrice(price);

        //repository.save(product);  метод не нужен, когда стоит аннотация Транзакционности

    }


    @Transactional
    public void deleteProduct(Long productId) {
        Product p = productRepository.getById(productId);
        productRepository.delete(p);
    }
}
