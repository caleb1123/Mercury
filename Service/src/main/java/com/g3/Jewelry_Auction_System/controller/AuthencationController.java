package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.request.*;
import com.g3.Jewelry_Auction_System.payload.response.AuthenticationResponse;
import com.g3.Jewelry_Auction_System.payload.response.IntrospectResponse;
import com.g3.Jewelry_Auction_System.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthencationController {

    @Autowired
    private AuthenticationService authenticationService;

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse response = authenticationService.introspect(request);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest logoutRequest) throws ParseException {
        authenticationService.logout(logoutRequest);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(result);
    }


    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> generateAndSendOtp(@RequestBody OTPRequest otpRequestDTO) {
        try {
            authenticationService.generateAndSendOtp(otpRequestDTO.getEmail());
            return ResponseEntity.ok("OTP sent successfully!");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending OTP: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPasswordWithOtp(@RequestBody ResetPasswordRequest resetPasswordRequestDTO) {
        try {
            authenticationService.resetPasswordWithOtp(
                    resetPasswordRequestDTO.getEmail(),
                    resetPasswordRequestDTO.getOtp(),
                    resetPasswordRequestDTO.getNewPassword()
            );
            return ResponseEntity.ok("Password reset successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/signupOTP")
    public ResponseEntity<String> activeAccountWithOTP(@RequestBody ResetPasswordRequest resetPasswordRequestDTO) {
        try {
            authenticationService.activeAccountWithOTP(
                    resetPasswordRequestDTO.getEmail(),
                    resetPasswordRequestDTO.getOtp()
            );
            return ResponseEntity.ok("Active account successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/sendOTPtoActive")
    public ResponseEntity<String> generateAndSendOtpforActive(@RequestBody OTPRequest otpRequestDTO) {
        try {
            authenticationService.generateAndSendOtpforActive(otpRequestDTO.getEmail());
            return ResponseEntity.ok("OTP sent successfully!");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending OTP: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/signUp")
    public ResponseEntity<AccountDTO> signUp(@RequestBody SignUpRequest accountDTO) {
        AccountDTO createdAccount = authenticationService.createAccountByUser(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
}