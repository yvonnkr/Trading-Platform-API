package com.yvolabs.tradingplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yvolabs.tradingplatform.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 24/10/2024
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

}
