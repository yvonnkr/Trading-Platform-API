package com.yvolabs.tradingplatform.controller;

import com.yvolabs.tradingplatform.domain.VerificationType;
import com.yvolabs.tradingplatform.dto.request.ForgotPasswordTokenRequest;
import com.yvolabs.tradingplatform.dto.request.ResetPasswordRequest;
import com.yvolabs.tradingplatform.dto.response.ApiResponse;
import com.yvolabs.tradingplatform.dto.response.AuthResponse;
import com.yvolabs.tradingplatform.model.ForgotPasswordToken;
import com.yvolabs.tradingplatform.model.User;
import com.yvolabs.tradingplatform.model.VerificationCode;
import com.yvolabs.tradingplatform.service.EmailService;
import com.yvolabs.tradingplatform.service.ForgotPasswordService;
import com.yvolabs.tradingplatform.service.UserService;
import com.yvolabs.tradingplatform.service.VerificationCodeService;
import com.yvolabs.tradingplatform.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 29/10/2024
 */

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final ForgotPasswordService forgotPasswordService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOTP(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable VerificationType verificationType) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOTPEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<>("OTP Sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,
                                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if (isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("OTP doesn't match");
    }

    @PostMapping("/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOTP(@RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user = userService.findUserProfileByEmail(req.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
        if (token == null) {
            token = forgotPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
        }

        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOTPEmail(user.getEmail(), token.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password Reset OTP sent successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id,
                                                     @RequestBody ResetPasswordRequest req) throws Exception {

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

        if (isVerified) {
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Password updated successfully!");
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }
        throw new Exception("OTP doesn't match");

    }

}
