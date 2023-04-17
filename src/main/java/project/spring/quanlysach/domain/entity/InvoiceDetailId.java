package project.spring.quanlysach.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class InvoiceDetailId implements Serializable {

    @ManyToOne
    private Product product;

    @ManyToOne
    private Invoice invoice;

}
