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
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Product")
public class Product extends AbstractAuditingEntity {

    @NotBlank(message = "Name data field is not null")
    @Column(nullable = false)
    private String name;
    private String seoTitle;

    private String status;

    @NotNull(message = "Price is not null")
    private float price;

    private float promotionPrice;

    private float vat;

    @NotNull(message = "Quantity is not null")
    private long quantity;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_BRAND_ID)
    private Brand brand;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_CATEGORY_ID)
    private Category category;
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<OrderTable> orders = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "invoiceDetail", joinColumns = @JoinColumn(name = CommonConstant.COLUMN_PRODUCT_ID),
            inverseJoinColumns = @JoinColumn(name = CommonConstant.COLUMN_INVOICE_ID))
    private List<Supplier> suppliers = new ArrayList<>();

    public Product(String name, String seoTitle, String status,
                   float price, float promotionPrice, float vat, long quantity,
                   int categoryId, int brandId) {
        this.name = name;
        this.seoTitle = seoTitle;
        this.status = status;
        this.price = price;
        this.promotionPrice = promotionPrice;
        this.vat = vat;
        this.quantity = quantity;
        this.brand.setId(brandId);
        this.category.setId(categoryId);
    }

    public Product(int id) {
        this.setId(id);
    }
}
