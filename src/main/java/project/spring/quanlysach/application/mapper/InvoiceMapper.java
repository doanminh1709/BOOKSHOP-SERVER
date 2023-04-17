package project.spring.quanlysach.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;
import project.spring.quanlysach.domain.dto.InvoiceDTO;
import project.spring.quanlysach.domain.entity.Invoice;
import project.spring.quanlysach.domain.entity.Supplier;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mappings({
            @Mapping(target = "status", source = "invoiceDTO.status"),
            @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "supplierOfInvoice"),
    })
    Invoice toInvoice(InvoiceDTO invoiceDTO);

    @Named("supplierOfInvoice")
    static Supplier supplierOfInvoice(@Name("supplierOfInvoice") int supplierId) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        return supplier;
    }
}
