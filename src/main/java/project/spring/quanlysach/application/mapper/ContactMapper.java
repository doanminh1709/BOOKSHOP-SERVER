package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import project.spring.quanlysach.domain.dto.ContactDTO;
import project.spring.quanlysach.domain.entity.Contact;
import project.spring.quanlysach.domain.entity.Customer;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Named("contactCustomer")
    static Customer contactCustomer(int customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

    @Mappings({
            @Mapping(target = "city", source = "contactDTO.city"),
            @Mapping(target = "detailAddress", source = "contactDTO.detailAddress"),
            @Mapping(target = "customer", source = "customerId", qualifiedByName = "contactCustomer")
    })
    Contact toContact(ContactDTO contactDTO, int customerId);
}
