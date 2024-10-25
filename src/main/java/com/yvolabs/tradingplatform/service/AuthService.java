package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.dto.response.AuthResponse;
import com.yvolabs.tradingplatform.model.User;
import jakarta.persistence.EntityExistsException;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */


public interface AuthService {
    String register(User registrationRequest) throws EntityExistsException;

    AuthResponse login(User user);
}
