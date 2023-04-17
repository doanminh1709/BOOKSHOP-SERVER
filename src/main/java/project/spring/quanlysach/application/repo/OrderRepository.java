package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.dto.StatisticalProduct;
import project.spring.quanlysach.domain.entity.OrderTable;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderTable, Integer> {

    List<OrderTable> findOrderByCustomerId(int customerId);

    OrderTable findOrderByCustomerIdAndProductId(int customerId, int productId);

    void deleteOrdersByProductIdAndCustomerId(int productId, int customerId);

//    @Query(nativeQuery = true)
//    List<StatisticalProduct>statisticalProductSellInDay2(@Param("time") String time);

}
