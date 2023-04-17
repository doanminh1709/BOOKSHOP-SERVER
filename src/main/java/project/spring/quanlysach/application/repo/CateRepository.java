package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.Cate;
import project.spring.quanlysach.domain.entity.Post;

@Repository
public interface CateRepository extends JpaRepository<Cate, Integer> {
        Cate findByName(String name);
}
