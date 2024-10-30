package com.yvolabs.tradingplatform.dto.request;

import lombok.Data;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

@Data
public class ResetPasswordRequest {
    private String otp;
    private String password;
}
