package com.yvolabs.tradingplatform.model;

import com.yvolabs.tradingplatform.domain.VerificationType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */

@NoArgsConstructor
@Data
@Embeddable
public class TwoFactorAuth {

    private boolean isEnabled = false;

    @Enumerated(EnumType.STRING)
    private VerificationType sendTo;
}
