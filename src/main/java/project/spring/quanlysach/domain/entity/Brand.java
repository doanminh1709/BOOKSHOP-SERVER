package project.spring.quanlysach.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Brand")
public class Brand extends AbstractAuditingEntity {

    @NotBlank(message = "Name data filed is not null")
//    @Column(nullable = false)
    private String name;
    private int yearOfEstablishment;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Product> products;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts = new HashSet<>();

    public Brand(String name, int yearOfEstablishment) {
        this.name = name;
        this.yearOfEstablishment = yearOfEstablishment;
    }

}
