package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.request.AuthenticationRequest;
import com.g3.Jewelry_Auction_System.payload.request.IntrospectRequest;
import com.g3.Jewelry_Auction_System.payload.request.LogoutRequest;
import com.g3.Jewelry_Auction_System.payload.request.RefreshTokenRequest;
import com.g3.Jewelry_Auction_System.payload.response.AuthenticationResponse;
import com.g3.Jewelry_Auction_System.payload.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest logoutRequest);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException;
}
