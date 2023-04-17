package project.spring.quanlysach.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "InvoiceDetail")
public class InvoiceDetail {

    @JsonIgnore
    @EmbeddedId
    private InvoiceDetailId invoiceDetailId;
    @JsonIgnore
    @ManyToOne
    @MapsId(CommonConstant.COLUMN_INVOICE_ID)
    @JoinColumn(name = CommonConstant.COLUMN_INVOICE_ID)
    private Invoice invoice;

    @JsonIgnore
    @ManyToOne
    @MapsId(CommonConstant.COLUMN_PRODUCT_ID)
    @JoinColumn(name = CommonConstant.COLUMN_PRODUCT_ID)
    private Product product;

    @NotBlank(message = "Name data field is not null")
    private String productName;
    private long quantity;
    private float price;
}
