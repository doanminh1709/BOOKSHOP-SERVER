package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.spring.quanlysach.domain.dto.SupplierDTO;
import project.spring.quanlysach.domain.entity.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mappings({
            @Mapping(target = "name", source = "supplierDTO.name"),
            @Mapping(target = "phone", source = "supplierDTO.phone"),
            @Mapping(target = "address", source = "supplierDTO.address")
    })
    Supplier toSupplier(SupplierDTO supplierDTO);

}
