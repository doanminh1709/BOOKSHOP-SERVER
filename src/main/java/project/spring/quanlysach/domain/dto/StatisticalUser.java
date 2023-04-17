package project.spring.quanlysach.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticalUser {
    private int customerId;
    private String customerName;
    private float totalMoney;

}
