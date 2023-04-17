package project.spring.quanlysach.adapter.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.spring.quanlysach.adapter.web.base.RestApiV0;
import project.spring.quanlysach.adapter.web.base.VsResponseUtil;
import project.spring.quanlysach.application.auth_service.LoginAccountCustomer;
import project.spring.quanlysach.application.auth_service.RefreshTokenService;
import project.spring.quanlysach.application.auth_service.RegistrationAccountService;
import project.spring.quanlysach.application.constants.UrlConstant;
import project.spring.quanlysach.application.request.LoginRequest;
import project.spring.quanlysach.application.request.TokenRefreshTokenRequest;
import project.spring.quanlysach.domain.dto.CustomerDTO;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestApiV0
public class AuthController {

    @Autowired
    private RegistrationAccountService registrationAccountService;
    @Autowired
    private LoginAccountCustomer loginAccountCustomer;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping(UrlConstant.Auth.SIGN_UP)
    @ApiOperation(value = "Register account's user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid CustomerDTO customerDTO) throws MessagingException {
        return VsResponseUtil.ok(registrationAccountService.register(customerDTO));
    }

    @GetMapping(UrlConstant.Auth.CONFIRM)
    @ApiOperation(value = "Confirm token send to email")
    public ResponseEntity<?> confirmToken(@RequestParam(value = "token", required = false) String token) {
        return VsResponseUtil.ok(registrationAccountService.confirmToken(token));
    }

    @GetMapping(UrlConstant.Auth.FORGOT_PASSWORD)
    @ApiOperation(value = "Get forgot password")
    public ResponseEntity<?> getForgotPassword(@PathVariable("customerId") int customerId) throws MessagingException {
        return VsResponseUtil.ok(registrationAccountService.getForgotPassword(customerId));
    }

    @PostMapping(UrlConstant.Auth.SIGN_IN)
    @ApiOperation(value = "Sign in account")
    public ResponseEntity<?> signInAccount(@RequestBody LoginRequest loginRequest) {
        return VsResponseUtil.ok(loginAccountCustomer.loginAccount(loginRequest));
    }

    @GetMapping(UrlConstant.Auth.SIGN_OUT)
    @ApiOperation(value = "Logout account")
    public ResponseEntity<?> logoutAccount() {
        return VsResponseUtil.ok(refreshTokenService.logoutAccount());
    }

    @PostMapping(UrlConstant.Auth.REFRESH_TOKEN)
    @ApiOperation(value = "Get access token by refresh token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshTokenRequest tokenRequest) {
        return VsResponseUtil.ok(refreshTokenService.refreshToken(tokenRequest));
    }
}
