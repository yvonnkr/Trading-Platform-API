package com.yvolabs.tradingplatform.model;


import com.yvolabs.tradingplatform.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

@Entity
@Data
public class ForgotPasswordToken {
    @Id
    private String id;

    @OneToOne
    private User user;

    private String otp;

    private VerificationType verificationType;

    private String sendTo;
}
