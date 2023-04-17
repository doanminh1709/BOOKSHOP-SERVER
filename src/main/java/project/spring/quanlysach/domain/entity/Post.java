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
public class Post extends AbstractAuditingEntity {

    @NotBlank(message = "Post name is not blank")
    @Column(nullable = false)
    private String name;

    private String seoTitle;

    private boolean status = Boolean.TRUE;//define is new feed

    private String description;

    @NotNull(message = "Detail field data is not null")
    private String detail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_BRAND_ID)
    private Brand brand;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CommonConstant.COLUMN_CATE_ID)
    private Cate cate;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PostComment> postComments = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "post_tag",
            joinColumns = @JoinColumn(name = CommonConstant.COLUMN_POST_ID),
            inverseJoinColumns = @JoinColumn(name = CommonConstant.COLUMN_TAG_ID))
    private List<Tag> tags = new ArrayList<>();

    public Post(int id) {
        this.setId(id);

    }

}
