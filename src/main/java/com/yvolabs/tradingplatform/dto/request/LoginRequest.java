package com.yvolabs.tradingplatform.dto.request;

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

    private String email;
    private String password;

}
