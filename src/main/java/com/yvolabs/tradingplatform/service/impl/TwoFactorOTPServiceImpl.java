package com.yvolabs.tradingplatform.service.impl;

import com.yvolabs.tradingplatform.model.TwoFactorOTP;
import com.yvolabs.tradingplatform.model.User;
import com.yvolabs.tradingplatform.repository.TwoFactorOTPRepository;
import com.yvolabs.tradingplatform.service.TwoFactorOTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 25/10/2024
 */

@Service
@RequiredArgsConstructor
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService {

    private final TwoFactorOTPRepository twoFactorOTPRepository;

    @Override
    public TwoFactorOTP createTwoFactorOTP(User user, String OTP, String jwt) {

        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setOTP(OTP);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);

        return twoFactorOTPRepository.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userID) {
        return twoFactorOTPRepository.findByUserId(userID);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> OTP = twoFactorOTPRepository.findById(id);
        return OTP.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String OTP) {
        return twoFactorOTP.getOTP().equals(OTP);
    }

    @Override
    public void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP) {
        twoFactorOTPRepository.delete(twoFactorOTP);
    }
}
