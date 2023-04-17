package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.ConfirmationToken;

@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationToken, Integer> {

}
