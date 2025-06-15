package com.warehouse.loginservice.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.warehouse.loginservice.dto.LoginRequest;
import com.warehouse.loginservice.dto.request.IntrospectRequest;
import com.warehouse.loginservice.dto.response.IntrospectResponse;
import com.warehouse.loginservice.exceptions.AppException;
import com.warehouse.loginservice.exceptions.ErrorCode;
import com.warehouse.loginservice.repository.RefreshTokenRepository;
import com.warehouse.loginservice.repository.UserRepository;
import com.warehouse.loginservice.service.AuthenticationService;
import com.warehouse.loginservice.service.JWTService;
import com.warehouse.loginservice.service.RefreshTokenService;
import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
        
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;

    JWTServiceImpl jwtService;

    RefreshTokenService refreshTokenService;

    AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        // Set token expiration time based on "Remember Me"
        Long expirationTime = loginRequest.isRememberMe()
                ? 1000L * 60 * 60 * 24 // 1 day
                : null; // Default expiration (30 minutes)

        var accessToken = jwtService.generateToken(user, expirationTime);
        var refreshToken = refreshTokenService.generateRefreshToken(loginRequest.getEmail());

        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .userRole(user.getRole().name())
                .userId(user.getUserId())
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            jwtService.verifyToken(token, true);
        } catch (JOSEException | ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }
}
