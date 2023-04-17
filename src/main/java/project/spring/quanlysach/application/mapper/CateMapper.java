package project.spring.quanlysach.application.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.spring.quanlysach.domain.dto.CateDTO;
import project.spring.quanlysach.domain.entity.Cate;

@Mapper(componentModel = "spring")
public interface CateMapper {

    @Mappings({
            @Mapping(target = "name" , source = "cateDTO.name"),
            @Mapping(target = "description" , source = "cateDTO.description"),
            @Mapping(target = "patentId", source = "cateDTO.patentId")
    })
    Cate toCate(CateDTO cateDTO);
}
