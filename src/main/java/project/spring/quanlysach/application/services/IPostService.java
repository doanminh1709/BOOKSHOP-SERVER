package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.PostDTO;
import project.spring.quanlysach.domain.entity.Post;

import java.util.List;

public interface IPostService {
    List<Post> listPost(Integer page, Integer size);

    List<Post> filterPostByBrand(int brandId);

    List<Post> filterPostByCate(String cateName);

    List<Post> showPostByLeastTime(Integer page, Integer size);

    Post createNewPost(PostDTO postDTO);

    String updatePostById(PostDTO postDTO, Integer id);

    Post getPostById(int id);

    String deletePostById(Integer id);

    List<Post> filterPostOfBrandAboutCateId(int brandId, int cateId);
}
