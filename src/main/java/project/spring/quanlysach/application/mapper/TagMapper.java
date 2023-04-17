package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.spring.quanlysach.domain.dto.TagDTO;
import project.spring.quanlysach.domain.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mappings({
            @Mapping(target = "name", source = "tagDTO.name"),
            @Mapping(target = "description", source = "tagDTO.description")
    })
    Tag toTag(TagDTO tagDTO);
}
