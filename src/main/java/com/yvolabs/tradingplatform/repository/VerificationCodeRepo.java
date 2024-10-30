package com.yvolabs.tradingplatform.repository;


import com.yvolabs.tradingplatform.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByUserId(Long userId);

}