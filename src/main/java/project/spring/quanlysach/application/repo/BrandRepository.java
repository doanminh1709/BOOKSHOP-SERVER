package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand , Integer> {

    Brand findByName(String name);
}
