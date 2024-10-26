package com.yvolabs.tradingplatform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */

@Getter
@Setter
@Builder
public class LoginRequest {

    private String fullName;
    private String email;
    private String password;

    //2fa
    private boolean isEnabled = false;
}
