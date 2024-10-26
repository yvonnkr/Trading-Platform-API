package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.model.TwoFactorOTP;
import com.yvolabs.tradingplatform.model.User;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 25/10/2024
 */

public interface TwoFactorOTPService {
    TwoFactorOTP createTwoFactorOTP(User user, String OTP, String jwt);

    TwoFactorOTP findByUser(Long userID);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String OTP);

    void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP);
}
