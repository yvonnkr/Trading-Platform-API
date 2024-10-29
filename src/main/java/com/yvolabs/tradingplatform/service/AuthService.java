package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.dto.request.LoginRequest;
import com.yvolabs.tradingplatform.dto.request.RegistrationRequest;
import com.yvolabs.tradingplatform.dto.response.AuthResponse;
import com.yvolabs.tradingplatform.model.User;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */


public interface AuthService {

    String register(RegistrationRequest registrationRequest) throws EntityExistsException;

    AuthResponse login(User user) throws MessagingException;

    AuthResponse verifySignOTP(String otp, String otpId) throws BadCredentialsException;
}
