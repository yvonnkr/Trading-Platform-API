package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.domain.VerificationType;
import com.yvolabs.tradingplatform.model.User;
import com.yvolabs.tradingplatform.model.VerificationCode;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
