package com.yvolabs.tradingplatform.service;

import com.yvolabs.tradingplatform.domain.VerificationType;
import com.yvolabs.tradingplatform.model.User;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */
public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserProfileByEmail(String email) throws Exception;

    User findUserProfileById(Long userId) throws Exception;

    User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);

    User updatePassword(User user, String newPassword);
}
