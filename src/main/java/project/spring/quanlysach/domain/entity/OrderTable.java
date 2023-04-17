package project.spring.quanlysach.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import javax.persistence.*;
import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "OrderTable")
public class OrderTable extends AbstractAuditingEntity {

    private boolean delivered = Boolean.FALSE;//define is delivered
    private Timestamp deliveredDate;
    private int count;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_PRODUCT_ID)
    private Product product;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_CUSTOMER_ID)
    private Customer customer;

    public OrderTable(Integer id, boolean delivered, Timestamp deliveredDate,
                      int count, boolean active_flag, boolean delete_flag,
                      Timestamp createDate, Timestamp updateFlag) {

        this.setId(id);
        this.delivered = delivered;
        this.deliveredDate = deliveredDate;
        this.count = count;
        this.setActiveFlag(active_flag);
        this.setDelivered(delete_flag);
        this.setCreateDate(createDate);
        this.setLastModifiedDate(updateFlag);
    }

}
