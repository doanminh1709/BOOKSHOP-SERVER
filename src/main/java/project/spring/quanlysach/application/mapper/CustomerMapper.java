package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.spring.quanlysach.domain.dto.CustomerDTO;
import project.spring.quanlysach.domain.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mappings({
            @Mapping(target = "fullName", source = "customerDTO.fullName"),
            @Mapping(target = "phone", source = "customerDTO.phone"),
            @Mapping(target = "address", source = "customerDTO.address"),
            @Mapping(target = "email", source = "customerDTO.email"),
            @Mapping(target = "username", source = "customerDTO.username"),
            @Mapping(target = "password", source = "customerDTO.password")
    })
    Customer toCustomer(CustomerDTO customerDTO);
}
