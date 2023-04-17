package project.spring.quanlysach.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Category")
public class Category extends AbstractAuditingEntity {
    private String name;
    private String seoTitle;
    private int parentId;
    @Nationalized
    private String description;
    private String detail;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Product> products = new ArrayList<>();

    public Category(String description, String detail,
                    String name, String seoTitle, int parentId) {
        this.description = description;
        this.detail = detail;
        this.name = name;
        this.parentId = parentId;
        this.seoTitle = seoTitle;
    }
}
