package com.yvolabs.tradingplatform.service.impl;

import com.yvolabs.tradingplatform.config.JwtProvider;
import com.yvolabs.tradingplatform.dto.response.AuthResponse;
import com.yvolabs.tradingplatform.model.User;
import com.yvolabs.tradingplatform.repository.UserRepository;
import com.yvolabs.tradingplatform.service.AuthService;
import com.yvolabs.tradingplatform.service.CustomUserDetailsService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public String register(User registrationRequest) {

        userRepository.findByEmail(registrationRequest.getEmail())
                .ifPresent(user -> {
                    throw new EntityExistsException("User already exists with email: " + user.getEmail());
                });

        User newUser = new User();
        newUser.setFullName((registrationRequest.getFullName()));
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                registrationRequest.getEmail(),
                registrationRequest.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return JwtProvider.generateToken(auth);


    }

    @Override
    public AuthResponse login(User user) {
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(userName)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login Successful");
        return res;

    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
