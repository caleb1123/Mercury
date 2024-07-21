package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.BlackListToken;
import com.g3.Jewelry_Auction_System.entity.OTPToken;
import com.g3.Jewelry_Auction_System.entity.Role;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.request.*;
import com.g3.Jewelry_Auction_System.payload.response.AuthenticationResponse;
import com.g3.Jewelry_Auction_System.payload.response.IntrospectResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.BlackListTokenRepository;
import com.g3.Jewelry_Auction_System.repository.OTPTokenRepository;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import com.g3.Jewelry_Auction_System.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.mail.MessagingException;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BlackListTokenRepository blackListTokenRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    OTPTokenRepository otpTokenRepository;
    @Autowired
    AccountConverter accountConverter;


    @Value("${app.jwt-secret}")
    private String SIGNER_KEY;

    @Value(value = "${app.jwt-access-expiration-milliseconds}")
    private long VALID_DURATION;

    @Value("${app.jwt-refresh-expiration-milliseconds}")
    private long REFRESHABLE_DURATION;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Account user = accountRepository
                .findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!user.getStatus()) throw new AppException(ErrorCode.ACCOUNT_INACTIVE);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.PASSWORD_NOT_CORRECT);

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();


        boolean isValid = true;
        try {
            verifyToken(token,false);
        } catch (AppException e){
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        try {
            var signToken = verifyToken(logoutRequest.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            BlackListToken invalidatedToken =
                    BlackListToken.builder().id(jit).expiryTime(expiryTime).build();

            blackListTokenRepository.save(invalidatedToken);
        } catch (AppException exception){
            log.info("Token already expired");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ParseException, JOSEException {
        var signedJWT = verifyToken(refreshTokenRequest.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        BlackListToken invalidatedToken =
                BlackListToken.builder().id(jit).expiryTime(expiryTime).build();

        blackListTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                accountRepository.findByUserName(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }
    @Transactional
    @Override
    public void generateAndSendOtp(String email) throws MessagingException {
        var account = accountRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.EMAIL_NOT_EXISTED)
        );

        String otp = String.valueOf(100000 + new Random().nextInt(900000)); // Generate a 6-digit OTP
        OTPToken otpToken = new OTPToken(); // OTP expires in 15 minutes
        otpToken.setEmail(email);
        otpToken.setOtp(otp);
        otpToken.setExpiryDate(Instant.now().plus(15,ChronoUnit.MINUTES));

        otpTokenRepository.save(otpToken);
        emailService.sendResetPasswordEmail(email,otp,account.getFullName());
    }

    @Override
    public void resetPasswordWithOtp(String email, String otp, String newPassword) {
        OTPToken otpToken = otpTokenRepository.findByEmailAndOtp(email, otp);
        if (otpToken == null || otpToken.isExpired()) {
            throw new IllegalArgumentException("Invalid or expired OTP");
        }

        var account = accountRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.EMAIL_NOT_EXISTED)
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        accountRepository.save(account);

        otpTokenRepository.delete(otpToken);
    }


    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (blackListTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUserName())
                .issuer("Mercury.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
                throw new RuntimeException(e);
        }
    }

    private String buildScope(Account account) {
        Role role = account.getRole();
        if (role != null) {
            return role.getRoleName().name(); // Assuming roleName is an enum or string
        }
        return "";
    }

    @Override
    public void activeAccountWithOTP(String email, String otp) {
        OTPToken otpToken = otpTokenRepository.findByEmailAndOtp(email, otp);
        if (otpToken == null || otpToken.isExpired()) {
            throw new IllegalArgumentException("Invalid or expired OTP");
        }

        var account = accountRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.EMAIL_NOT_EXISTED)
        );

        account.setStatus(true);

        accountRepository.save(account);

        otpTokenRepository.delete(otpToken);
    }
    @Transactional
    @Override
    public void generateAndSendOtpforActive(String email) throws MessagingException {
        var account = accountRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.EMAIL_NOT_EXISTED)
        );

        String otp = String.valueOf(100000 + new Random().nextInt(900000)); // Generate a 6-digit OTP
        OTPToken otpToken = new OTPToken(); // OTP expires in 15 minutes
        otpToken.setEmail(email);
        otpToken.setOtp(otp);
        otpToken.setExpiryDate(Instant.now().plus(15,ChronoUnit.MINUTES));

        otpTokenRepository.save(otpToken);
        emailService.sendOTPtoActiveAccount(email,otp,account.getFullName());
    }


    @Override
    public AccountDTO createAccountByUser(SignUpRequest signUpRequest) {
        Account existingUserEmail = accountRepository.findByEmail(signUpRequest.getEmail()).orElse(null);
        Account existingUserPhone = accountRepository.findByPhone(signUpRequest.getPhone()).orElse(null);
        Account existingUserName = accountRepository.findByUserName(signUpRequest.getUserName()).orElse(null);
        if (existingUserEmail != null) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        if (existingUserPhone != null) {
            throw new AppException(ErrorCode.PHONE_TAKEN);
        }
        if (existingUserName != null) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPassword);

        Account createAccount = accountConverter.toEntity(signUpRequest);

        Role userRole = roleRepository.findById(4)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        createAccount.setRole(userRole);
        createAccount.setStatus(false);

        accountRepository.save(createAccount);

        return accountConverter.toDTO(createAccount);
    }

}
