package project.spring.quanlysach.application.auth_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.request.LoginRequest;
import project.spring.quanlysach.application.response.JwtResponse;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.config.filter.JwtUtils;
import project.spring.quanlysach.domain.entity.RefreshToken;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginAccountCustomer {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtUtils jwtUtils;

    public JwtResponse loginAccount(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomerDetailImp customerDetailImp = (CustomerDetailImp) authentication.getPrincipal();

        if (customerDetailImp != null) {
            if (customerRepository.checkCustomerEnable(customerDetailImp.getId())) {
                List<String> roles = customerDetailImp.getAuthorities().stream().
                        map(GrantedAuthority::getAuthority).collect(Collectors.toList());

                String accessToken = jwtUtils.generateJwtToken(customerDetailImp);
                RefreshToken refreshToken = refreshTokenService.createNewRefreshToken(customerDetailImp.getId());
                return new JwtResponse(customerDetailImp.getId(),
                        customerDetailImp.getFullName(),
                        customerDetailImp.getEmail(),
                        customerDetailImp.getPhone(),
                        customerDetailImp.getUsername(),
                        roles, accessToken, refreshToken.getToken());
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.ACCOUNT_IS_NOT_ENABLE);
            }
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    DevMessageConstant.Common.OBJECT_NOT_FOUND);
        }
    }
}
