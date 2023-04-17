package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.ConfirmationToken;
import project.spring.quanlysach.domain.entity.Customer;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    ConfirmationToken findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken  u SET u.confirmAt= ?1 WHERE u.token = ?2")
    int updateConfirmationAt(LocalDateTime confirmAt, String token);

    ConfirmationToken findByCustomer(Customer customer);

    String deleteByCustomer(Customer customer);
}
