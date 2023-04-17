package project.spring.quanlysach.application.services;

import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.domain.dto.CustomerDTO;
import project.spring.quanlysach.domain.dto.PostCommentDTO;
import project.spring.quanlysach.domain.entity.PostComment;

import java.util.List;
import java.util.Optional;

public interface IPostCommentService {
    PostComment createNewCommentOfPost(PostCommentDTO postCommentDTO, MultipartFile multipartFile);

    List<PostComment> showAllCommentOfPost(Integer page, Integer size);

    List<PostComment> getAllCommentByPostId(int postId);

    List<PostComment> getAllCommentByCustomer(int customerId);

    Optional<PostComment> getCommentById(int id);

    String editCommentById(PostCommentDTO postCommentDTO, int id, MultipartFile multipartFile);

    String removeCommentById(int id);

    List<PostComment> showAllCommentLeastNewOfPost(int postId);

    List<PostComment> showAllCommentOfCustomerInAPost(int customerId , int postId);
}
