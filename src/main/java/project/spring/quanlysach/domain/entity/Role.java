package project.spring.quanlysach.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.application.constants.EnumRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumRole roleName;
    @ManyToMany(mappedBy = "roles")
    private List<Customer> customers = new ArrayList<>();

    public Role(int id, EnumRole roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
