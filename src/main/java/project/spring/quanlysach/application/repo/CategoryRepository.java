package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {
    Category findCategoriesByName(String name);

    Optional<Category> findById(Integer id);

}
