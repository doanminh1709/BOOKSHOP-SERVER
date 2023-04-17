package project.spring.quanlysach.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.domain.entity.Image;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String name;
    private String seoTitle;
    private String status;
    private float price;
    private float promotionPrice;
    private float vat;
    private long quantity;
    private int categoryId;
    private int brandId;
}
