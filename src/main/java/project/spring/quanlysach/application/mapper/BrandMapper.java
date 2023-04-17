package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.spring.quanlysach.domain.dto.BrandDTO;
import project.spring.quanlysach.domain.entity.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {
//    @Mappings({
//            @Mapping(target = "name" , source = "brandDto.name"),
//            @Mapping(target = "yearOfEstablishment" , source = "brandDto.yearOfEstablishment"),
//    })
    Brand toBrand(BrandDTO brandDto);
}
