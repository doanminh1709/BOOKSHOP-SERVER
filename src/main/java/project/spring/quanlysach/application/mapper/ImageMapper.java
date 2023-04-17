package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import project.spring.quanlysach.domain.dto.ImageDTO;
import project.spring.quanlysach.domain.entity.Image;
import project.spring.quanlysach.domain.entity.Post;
import project.spring.quanlysach.domain.entity.Product;

@Mapper(componentModel = "spring")
public interface ImageMapper {

//    @Named("imageOfPost")
//    static Post imageOfPost(int postId) {
//        Post post = new Post();
//        post.setId(postId);
//        return post;
//    }

//    @Named("imageOfProduct")
//    static Product imageOfProduct(int productId) {
//        Product product = new Product();
//        product.setId(productId);
//        return product;
//    }

    @Mappings({
            @Mapping(target = "title", source = "imageDTO.title"),
            @Mapping(target = "path", source = "imageDTO.path")
//            @Mapping(target = "post", source = "postId", qualifiedByName = "imageOfPost", defaultValue = "0"),
//            @Mapping(target = "product", source = "productId", qualifiedByName = "imageOfProduct", defaultValue = "0")
    })
    Image toImage(ImageDTO imageDTO);

}
