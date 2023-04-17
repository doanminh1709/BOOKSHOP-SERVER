package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;
import project.spring.quanlysach.domain.dto.PostDTO;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.Cate;
import project.spring.quanlysach.domain.entity.Post;
import project.spring.quanlysach.domain.entity.Tag;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mappings({
            @Mapping(target = "name", source = "postDTO.name"),
            @Mapping(target = "seoTitle", source = "postDTO.seoTitle"),
            @Mapping(target = "status", source = "postDTO.status"),
            @Mapping(target = "description", source = "postDTO.description"),
            @Mapping(target = "cate", source = "cateId", qualifiedByName = "toCate"),
            @Mapping(target = "brand", source = "brandId", qualifiedByName = "toBrand"),
            @Mapping(target = "tags", source = "tagId", qualifiedByName = "toTag"),
    })
    Post toPost(PostDTO postDTO, @Name("toCate") int cateId,
                @Name("toBrand") int brandId, @Name("toTag") int tagId);

    @Named("toCate")
    static Cate toCate(int cateId) {
        Cate cate = new Cate();
        cate.setId(cateId);
        return cate;
    }

    @Named("toBrand")
    static Brand toBrand(int brandId) {
        Brand brand = new Brand();
        brand.setId(brandId);
        return brand;
    }

    @Named("toTag")
    static List<Tag> toTag(int tagId) {
        List<Tag>listTag = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(tagId);
        listTag.add(tag);
        return listTag;
    }

}
