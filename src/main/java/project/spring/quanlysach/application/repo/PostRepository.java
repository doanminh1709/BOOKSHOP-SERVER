package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post , Integer> {

    List<Post>findByBrandId(int brandId);

    Post findByName(String name);

    List<Post> findPostByCateId(int cateId);

    List<Post> findPostByBrandIdAndCateId(int brandId , int cateId);
}
