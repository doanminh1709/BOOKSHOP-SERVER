package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag , Integer> {
    Tag findByName(String tagName);
}
