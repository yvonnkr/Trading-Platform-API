package com.yvolabs.tradingplatform.dto.request;

import com.yvolabs.tradingplatform.domain.VerificationType;
import lombok.Data;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
