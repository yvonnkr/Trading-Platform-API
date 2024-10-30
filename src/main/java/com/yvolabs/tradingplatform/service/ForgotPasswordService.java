package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.domain.VerificationType;
import com.yvolabs.tradingplatform.model.ForgotPasswordToken;
import com.yvolabs.tradingplatform.model.User;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
