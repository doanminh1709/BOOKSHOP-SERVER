package project.spring.quanlysach.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Supplier")
public class Supplier extends AbstractAuditingEntity {
    @NotBlank(message = "Name data field is not null")
    @Column(nullable = false, unique = true)
    private String name;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number have just 10 characters")
    @Column(nullable = false, unique = true)
    private String phone;

    @Email(message = "This email is not correct")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Address is not blank")
    private String address;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Invoice> invoices = new HashSet<>();

    @ManyToMany(mappedBy = "suppliers")
    private Set<Product> products = new HashSet<>();

    public Supplier(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

}

