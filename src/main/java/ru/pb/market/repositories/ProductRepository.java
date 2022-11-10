package ru.pb.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pb.market.data.Product;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {



//    @Query("select p from Product p where p.price between ?1 and ?2 and p.title like ?3 and p.id < ?4")
//      @Query("select p from Product p where p.price between :priceStart and :priceEnd and p.title like :title and p.id < :id")
//    List<Product> findByPriceBetweenAndTitleLikeAndIdIsLessThan(int priceStart, int priceEnd, String title, long id);


    boolean existsById(Long id);
    List<Product> getProductByIdIn(Long[] id);

}
