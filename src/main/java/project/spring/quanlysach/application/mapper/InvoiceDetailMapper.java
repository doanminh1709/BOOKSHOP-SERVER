package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.data.repository.query.Param;
import project.spring.quanlysach.domain.dto.InvoiceDetailDTO;
import project.spring.quanlysach.domain.entity.Invoice;
import project.spring.quanlysach.domain.entity.InvoiceDetail;
import project.spring.quanlysach.domain.entity.InvoiceDetailId;
import project.spring.quanlysach.domain.entity.Product;


@Mapper(componentModel = "spring")
public interface InvoiceDetailMapper {

    @Mappings({
            @Mapping(target = "productName", source = "invoiceDetailDTO.productName"),
            @Mapping(target = "quantity", source = "invoiceDetailDTO.quantity"),
            @Mapping(target = "price", source = "invoiceDetailDTO.price"),
    })
    InvoiceDetail toInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO);

}
