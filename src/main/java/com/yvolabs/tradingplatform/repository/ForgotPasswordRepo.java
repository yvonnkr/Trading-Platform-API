package com.yvolabs.tradingplatform.repository;

import com.yvolabs.tradingplatform.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordToken, String> {
    ForgotPasswordToken findByUserId(Long userId);
}
