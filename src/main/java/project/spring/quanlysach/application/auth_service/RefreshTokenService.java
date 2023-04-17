package project.spring.quanlysach.application.auth_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.repo.RefreshTokenRepository;
import project.spring.quanlysach.application.request.TokenRefreshTokenRequest;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.config.filter.JwtUtils;
import project.spring.quanlysach.domain.entity.Customer;
import project.spring.quanlysach.domain.entity.RefreshToken;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtils jwtUtils;

    //find by token
    public RefreshToken findRefreshTokenByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    //create refresh token
    public RefreshToken createNewRefreshToken(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
        }
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setCustomer(customer.get());
        newRefreshToken.setToken(UUID.randomUUID().toString());
        long refreshTokenDurations = 3600000L;
        newRefreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurations));
        return refreshTokenRepository.save(newRefreshToken);
    }

    //verify token
    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    DevMessageConstant.Common.REFRESH_TOKEN_EXPIRED);
        }
        return true;
    }

    //delete by user
    @Transactional
    public void deleteByCustomer(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
        }
        refreshTokenRepository.deleteByCustomerId(customerId);
    }

    public String refreshToken(TokenRefreshTokenRequest tokenRefresh) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(tokenRefresh.getRefreshToken());
        if (refreshToken != null && verifyExpiration(refreshToken)) {
            Optional<Customer> customer = customerRepository.findById(refreshToken.getCustomer().getId());
            return jwtUtils.generateToken(customer.get().getUsername());

        }
        return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
    }

    public String logoutAccount() {
        CustomerDetailImp customerDetailImp = (CustomerDetailImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        deleteByCustomer(customerDetailImp.getId());
        return DevMessageConstant.Common.LOGOUT;
    }

}
