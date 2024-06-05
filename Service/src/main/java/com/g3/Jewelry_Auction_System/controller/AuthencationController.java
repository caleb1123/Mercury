package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.request.AuthenticationRequest;
import com.g3.Jewelry_Auction_System.payload.request.IntrospectRequest;
import com.g3.Jewelry_Auction_System.payload.request.LogoutRequest;
import com.g3.Jewelry_Auction_System.payload.request.RefreshTokenRequest;
import com.g3.Jewelry_Auction_System.payload.response.AuthenticationResponse;
import com.g3.Jewelry_Auction_System.payload.response.IntrospectResponse;
import com.g3.Jewelry_Auction_System.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthencationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);

    }
    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
            IntrospectResponse response = authenticationService.introspect(request);
            return ResponseEntity.ok(response);

    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest logoutRequest) throws ParseException {
        authenticationService.logout(logoutRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(result);
    }
}
