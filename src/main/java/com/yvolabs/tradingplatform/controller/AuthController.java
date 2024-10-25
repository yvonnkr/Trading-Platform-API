package com.yvolabs.tradingplatform.controller;

import com.yvolabs.tradingplatform.dto.response.AuthResponse;
import com.yvolabs.tradingplatform.model.User;
import com.yvolabs.tradingplatform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody User registrationRequest) {

        String jwt = this.authService.register(registrationRequest);
        AuthResponse res = AuthResponse.builder()
                .jwt(jwt)
                .status(true)
                .message("Registration successful")
                .build();

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {

        AuthResponse res = this.authService.login(user);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
