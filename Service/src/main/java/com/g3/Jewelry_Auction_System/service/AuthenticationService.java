package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.request.*;
import com.g3.Jewelry_Auction_System.payload.response.AuthenticationResponse;
import com.g3.Jewelry_Auction_System.payload.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest logoutRequest);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException;

    void generateAndSendOtp(String email) throws MessagingException;

    void resetPasswordWithOtp(String email, String otp, String newPassword);

    void activeAccountWithOTP(String email, String otp);

    public void generateAndSendOtpforActive(String email) throws MessagingException;
    AccountDTO createAccountByUser(SignUpRequest signUpRequest);

}
