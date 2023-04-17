package project.spring.quanlysach.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.application.constants.CommonConstant;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ConfirmationToken")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime expressAt;

    @Column(nullable = true)
    private LocalDateTime confirmAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = CommonConstant.COLUMN_CUSTOMER_ID)
    private Customer customer;

    public ConfirmationToken(String token, LocalDateTime createDate,
                             LocalDateTime expressAt, Customer customer) {
        this.token = token;
        this.createDate = createDate;
        this.expressAt = expressAt;
        this.customer = customer;
    }
    /*
    Su khac biet giũa timestamp và localdatetime
       LocalDateTime : sử dụng để biểu diễn các sự kiện cụ thể trong địa phương (ngày sinh , ngày kết hôn , tg đăng nhập)
       TimeStamp : lưu trữ và đại diện cho thời gian được dồng độ hóa trên toàn thế giời ,
       sử dụng trong cơ sở dữ liệu và ứng dụng web( thời gian tạo , thời gian cập nhật và tính toán các khoảng tg)
    */

}

