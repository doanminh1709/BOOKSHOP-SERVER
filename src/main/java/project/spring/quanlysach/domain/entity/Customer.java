package project.spring.quanlysach.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.spring.quanlysach.application.constants.CommonConstant;
import project.spring.quanlysach.domain.entity.base.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer extends AbstractAuditingEntity {

    @NotBlank(message = "FullName is not blank")
    private String fullName;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 characters")
    @NotBlank
    private String phone;
    private String address;
//    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty(message = "Username must not be empty")
    @Size(min = 6, max = 20, message = "Username must be between 6 and 20 characters")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=\\[\\]{};:'\"<>,.?/]).{8,}$",
            message = "Password is not valid")
    private String password;

    private boolean locked = Boolean.FALSE;

    private boolean enable = Boolean.FALSE;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<OrderTable> orders = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = CommonConstant.COLUMN_CUSTOMER_ID),
            inverseJoinColumns = @JoinColumn(name = CommonConstant.COLUMN_ROLE_ID))
    private List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contacts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConfirmationToken> confirmationTokens = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostComment> postComments = new ArrayList<>();

    public Customer(String fullName, String phone, String address, String email, String username, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
