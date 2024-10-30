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
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    @OneToOne
    private User user;

    private String email;

    private String mobile;

    private VerificationType verificationType;
}
