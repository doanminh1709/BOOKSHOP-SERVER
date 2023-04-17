package project.spring.quanlysach.application.repo;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByName(String productName);

    List<Product> findProductByStatus(String status);

    List<Product> findProductByCategoryId(int categoryId);

    List<Product> findProductByBrandId(int brandId);

    @Query("SELECT u FROM Product u WHERE u.name LIKE :name_product")
    List<Product> searchProductByName(@Param("name_product") String name);

    @Query("SELECT u FROM Product u WHERE u.price >= :start_point")
    List<Product> selectProductHigherPrice(@Param("start_point") Float start);

    @Query("SELECT u FROM Product u WHERE u.price >= :start_point AND u.price <= :end_point")
    List<Product> selectProductBetweenPrice(@Param("start_point") Float start, @Param("end_point") Float end);

    @Query("SELECT u FROM Product u WHERE u.price <= :end_point")
    List<Product> selectProductSmallerPrice( @Param("end_point") Float end);

}
