package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.PostComment;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
    List<PostComment> findPostCommentByPostId(int postId);

    List<PostComment> findPostCommentByCustomerId(int customerId);

    @Query("SELECT u FROM PostComment  u WHERE u.post.id =:postId ORDER BY u.createDate DESC")
    List<PostComment> findPostCommentByPostIdSoftDESC(@Param("postId") int postId);

    List<PostComment> findPostCommentByCustomerIdAndPostId(int customerId, int postId);
}
