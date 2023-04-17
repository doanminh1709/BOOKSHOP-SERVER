package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;
import project.spring.quanlysach.domain.dto.OrderDTO;
import project.spring.quanlysach.domain.entity.Customer;
import project.spring.quanlysach.domain.entity.OrderTable;
import project.spring.quanlysach.domain.entity.Product;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Named("productInCart")
    static Product productInCart(int productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @Named("cardOfCustomer")
    static Customer cardOfCustomer(int customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

    @Mappings({
            @Mapping(target = "count", source = "orderDTO.count"),
            @Mapping(target = "product", source = "productId", qualifiedByName = "productInCart"),
            @Mapping(target = "customer", source = "customerId", qualifiedByName = "cardOfCustomer"),
    })
    OrderTable toOrderMapper(OrderDTO orderDTO,
                             @Name("productInCart") int productId,
                             @Name("cardOfCustomer") int customerId);
}
