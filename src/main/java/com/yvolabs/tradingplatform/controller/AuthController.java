package com.yvolabs.tradingplatform.controller;

import com.yvolabs.tradingplatform.dto.request.LoginRequest;
import com.yvolabs.tradingplatform.dto.request.RegistrationRequest;
import com.yvolabs.tradingplatform.dto.response.AuthResponse;
import com.yvolabs.tradingplatform.model.User;
import com.yvolabs.tradingplatform.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {

        String jwt = this.authService.register(registrationRequest);
        AuthResponse res = AuthResponse.builder()
                .jwt(jwt)
                .status(true)
                .message("Registration successful")
                .build();

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody User loginRequest) throws MessagingException {

        AuthResponse res = this.authService.login(loginRequest);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigninOTP(@PathVariable String otp, @RequestParam String otpId) throws BadCredentialsException {
        AuthResponse res = this.authService.verifySignOTP(otp, otpId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
