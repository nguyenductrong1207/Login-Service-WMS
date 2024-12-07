package com.warehouse.loginservice.service.impl;

import com.warehouse.loginservice.dto.LoginRequest;
import com.warehouse.loginservice.repository.UserRepository;
import com.warehouse.loginservice.service.AuthenticationService;
import com.warehouse.loginservice.service.RefreshTokenService;
import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final JWTServiceImpl jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

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
                .build();
    }

}
