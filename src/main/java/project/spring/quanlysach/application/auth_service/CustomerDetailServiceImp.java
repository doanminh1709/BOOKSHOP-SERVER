package project.spring.quanlysach.application.auth_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.config.filter.JwtUtils;
import project.spring.quanlysach.domain.entity.Customer;

import javax.transaction.Transactional;

@Service
public class CustomerDetailServiceImp implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(customer)) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Customer.NOT_FOUND_CUSTOMER_BY_USERNAME, username));
        }
        return CustomerDetailImp.map(customer);
    }
}
