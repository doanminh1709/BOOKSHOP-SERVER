package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Image;
import project.spring.quanlysach.domain.entity.Post;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImageByPostId(int postId);
    List<Image> findImageByProductId(int id);
}
