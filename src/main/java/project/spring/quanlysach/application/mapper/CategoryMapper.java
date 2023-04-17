package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.spring.quanlysach.domain.dto.CategoryDTO;
import project.spring.quanlysach.domain.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
//    @Mappings({
//            @Mapping(target = "name", source = "categoryDTO.name"),
//            @Mapping(target = "seoTitle", source = "categoryDTO.seoTitle"),
//            @Mapping(target = "patentId", source = "categoryDTO.patentId"),
//            @Mapping(target = "metaKeyWord", source = "categoryDTO.metaKeyWord"),
//            @Mapping(target = "quantity", source = "categoryDTO.quantity"),
//            @Mapping(target = "hot", source = "categoryDTO.hot"),
//            @Mapping(target = "description", source = "categoryDTO.description"),
//            @Mapping(target = "detail", source = "categoryDTO.detail"),
//    })
    Category toCategory(CategoryDTO categoryDTO);
}
