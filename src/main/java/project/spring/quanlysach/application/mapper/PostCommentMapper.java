package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import project.spring.quanlysach.domain.dto.PostCommentDTO;
import project.spring.quanlysach.domain.entity.Customer;
import project.spring.quanlysach.domain.entity.Post;
import project.spring.quanlysach.domain.entity.PostComment;

@Mapper(componentModel = "spring")
public interface PostCommentMapper {
    @Mappings({
            @Mapping(target = "customer", source = "postCommentDTO.customerId", qualifiedByName = "customerComment"),
            @Mapping(target = "detail", source = "postCommentDTO.detail"),
            @Mapping(target = "feeling", source = "postCommentDTO.feeling"),
            @Mapping(target = "pathImage", source = "postCommentDTO.linkUrl"),
            @Mapping(target = "post", source = "postId", qualifiedByName = "postComment")
    })
    PostComment toPostComment(PostCommentDTO postCommentDTO, int postId, int customerId);

    @Named("postComment")
    static Post postComment(int postId) {
        Post post = new Post();
        post.setId(postId);
        return post;
    }

    @Named("customerComment")
    static Customer customerComment(int customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;

    }

}
