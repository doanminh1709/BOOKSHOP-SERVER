package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("SELECT u FROM Supplier  u WHERE u.name LIKE :name")
    Supplier findSupplierByName(@Param("name") String name);

    Supplier findSupplierByPhone( String phone);

    @Query("SELECT u FROM Supplier  u WHERE u.address LIKE :address")
    List<Supplier> findSupplierByAddress(@Param("address") String address);


}
