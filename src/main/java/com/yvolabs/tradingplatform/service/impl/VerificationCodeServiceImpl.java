package com.yvolabs.tradingplatform.service.impl;

import com.yvolabs.tradingplatform.domain.VerificationType;
import com.yvolabs.tradingplatform.model.User;
import com.yvolabs.tradingplatform.model.VerificationCode;
import com.yvolabs.tradingplatform.repository.VerificationCodeRepo;
import com.yvolabs.tradingplatform.service.VerificationCodeService;
import com.yvolabs.tradingplatform.utils.OtpUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOtp());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);

        return verificationCodeRepo.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) {
        return verificationCodeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Verification code not found"));
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepo.findByUserId(userId)
                .orElse(null);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepo.delete(verificationCode);
    }
}
