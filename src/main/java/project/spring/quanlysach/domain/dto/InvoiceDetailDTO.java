package project.spring.quanlysach.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.domain.entity.InvoiceDetail;
import project.spring.quanlysach.domain.entity.InvoiceDetailId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDetailDTO {

    private String productName;
    private int quantity;
    private float price;
    private int invoiceId;
    private int productId;

}
