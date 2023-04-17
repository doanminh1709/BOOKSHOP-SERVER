package project.spring.quanlysach.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PostComment")
public class PostComment extends AbstractAuditingEntity {

    @NotBlank(message = "Field detail is not null")
    private String detail;
    private String pathImage;
    private String feeling;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_POST_ID)
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_CUSTOMER_ID)
    private Customer customer;

}
