package ru.pb.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.pb.market.dto.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {




//    @Query("select p from Product p where p.price between ?1 and ?2 and p.title like ?3 and p.id < ?4")
    @Query("select p from Product p where p.price between :priceStart and :priceEnd and p.title like :title and p.id < :id")
    List<Product> findByPriceBetweenAndTitleLikeAndIdIsLessThan(int priceStart, int priceEnd, String title, long id);


    List<Product> findAllByPriceBetween(int priceStart, int priceEnd);
    @Query("select p from Product p where p.price < 80")
    List<Product> findLowPriceProducts();

    Optional<Product> findProductByTitle(String title);
}
