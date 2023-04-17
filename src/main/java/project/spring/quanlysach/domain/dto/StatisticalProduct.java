package project.spring.quanlysach.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalProduct {
    private int productId;
    private String nameProduct;
    private int count;

    @Override
    public String toString() {
        return "StatisticalProduct{" +
                "productId=" + productId +
                ", nameProduct='" + nameProduct + '\'' +
                ", count=" + count +
                '}';
    }
}
