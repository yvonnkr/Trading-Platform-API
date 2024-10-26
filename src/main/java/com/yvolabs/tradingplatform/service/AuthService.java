package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.dto.request.LoginRequest;
import com.yvolabs.tradingplatform.dto.request.RegistrationRequest;
import com.yvolabs.tradingplatform.dto.response.AuthResponse;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */


public interface AuthService {

    String register(RegistrationRequest registrationRequest) throws EntityExistsException;

    AuthResponse login(LoginRequest user) throws MessagingException;
}
