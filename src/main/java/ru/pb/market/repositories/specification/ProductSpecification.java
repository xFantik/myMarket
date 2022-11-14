package ru.pb.market.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.pb.market.data.Product;

public class ProductSpecification {
    public static Specification<Product> priceGreaterOrEquals(Integer price) {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price)));
    }

    public static Specification<Product> priceLessOrEquals(Integer price) {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price)));
    }

    public static Specification<Product> nameLike(String titlePart) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart)))));
    }

    public static Specification<Product> isActive(){
        return (((((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isActive"))))));
//        return (((((root, query, criteriaBuilder) -> criteriaBuilder.l
    }

}
