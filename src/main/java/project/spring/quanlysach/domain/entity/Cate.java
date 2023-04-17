package project.spring.quanlysach.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Cate")
public class Cate extends AbstractAuditingEntity {

    @NotBlank(message = "Name data filed is not null")
    private String name;

    @NotEmpty(message = "Description is not null")
    private String description;

    private int patentId;

    @JsonIgnore
    @OneToMany(mappedBy = "cate", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public Cate(String name, String description, int patentId) {
        this.name = name;
        this.description = description;
        this.patentId = patentId;
    }
}
