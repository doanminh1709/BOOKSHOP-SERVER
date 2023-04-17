package project.spring.quanlysach.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Tag")
public class Tag extends AbstractAuditingEntity {
    private String name;

    private String description;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
