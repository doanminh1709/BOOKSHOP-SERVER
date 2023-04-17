package project.spring.quanlysach.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Image")
public class Image extends AbstractAuditingEntity {

    @NotEmpty(message = "Enter missing title")
    private String title;
    private String path;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_POST_ID)
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_PRODUCT_ID)
    private Product product;
}
