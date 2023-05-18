package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Customer;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT a.enable FROM Customer a WHERE  a.id = ?1")
    boolean checkCustomerEnable(int customerId);

    Customer findCustomerByUsernameAndEmail(String username , String mail);

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.enable = TRUE WHERE c.id = ?1")
    void enableUser(int customerId);

    @Query("SELECT u FROM Customer  u WHERE u.fullName lIKE :name")
    List<Customer> findCustomerByName(@Param("name") String name);


    @Query("SELECT u FROM Customer  u WHERE u.username = ?1")
    Customer findByUsername(String username);

    @Query("SELECT c FROM Customer c")
    List<Customer> findCustomerByCity(String city);

}
