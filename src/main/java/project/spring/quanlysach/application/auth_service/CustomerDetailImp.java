package project.spring.quanlysach.application.auth_service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.spring.quanlysach.domain.entity.Customer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDetailImp implements UserDetails {
    private static final long serialVersionUID = 1L;
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomerDetailImp map(Customer customer) {
        List<GrantedAuthority> authorities = customer.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
        //GrantedAuthority : is a interface present a granted authority
        //SimpleGrantedAuthority : object present of granted authority
        return new CustomerDetailImp(customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getUsername(),
                customer.getPassword(),
                authorities);
    }

    @Override//return a collection that user provided
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDetailImp)) return false;
        CustomerDetailImp that = (CustomerDetailImp) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isAccountNonExpired() {//Account has not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//Account has not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//Credentials has not expired
        return true;
    }

    @Override
    public boolean isEnabled() {//Account is enabled
        return true;
    }
}
