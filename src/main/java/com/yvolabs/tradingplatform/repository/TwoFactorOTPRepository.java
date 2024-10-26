package com.yvolabs.tradingplatform.repository;

import com.yvolabs.tradingplatform.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 25/10/2024
 */

public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long userId);
}
