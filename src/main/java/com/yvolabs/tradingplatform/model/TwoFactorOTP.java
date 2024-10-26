package com.yvolabs.tradingplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 25/10/2024
 * @apiNote two factor one time password
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class TwoFactorOTP {

    @Id
    private String id;

    private String OTP;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jwt;
}
