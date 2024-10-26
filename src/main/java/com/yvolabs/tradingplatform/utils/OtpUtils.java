package com.yvolabs.tradingplatform.utils;

import java.util.Random;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 25/10/2024
 */
public class OtpUtils {
    public static String generateOtp() {
        int otpLength = 6;
        Random random = new Random();
        StringBuilder OTP = new StringBuilder(otpLength);

        for(int i=0;i<otpLength;i++) {
            OTP.append(random.nextInt(10));
        }

        return OTP.toString();
    }
}
