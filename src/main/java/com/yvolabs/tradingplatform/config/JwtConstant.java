package com.yvolabs.tradingplatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */

@Service
public class JwtConstant {
    public static String SECRET_KEY;
    public static final String JWT_HEADER = "Authorization";

    @Value("${application.security.jwt.secret-key}")
    public void setSecretKey(String key) {
        SECRET_KEY = key;
    }

}
