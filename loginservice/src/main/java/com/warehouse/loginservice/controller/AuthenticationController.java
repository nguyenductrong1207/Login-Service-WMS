package com.warehouse.loginservice.controller;

import com.warehouse.loginservice.dto.ApiResponse;
import com.warehouse.loginservice.dto.LoginRequest;
import com.warehouse.loginservice.dto.request.IntrospectRequest;
import com.warehouse.loginservice.dto.response.IntrospectResponse;
import com.warehouse.loginservice.entity.RefreshToken;
import com.warehouse.loginservice.entity.User;
import com.warehouse.loginservice.service.AuthenticationService;
import com.warehouse.loginservice.service.impl.JWTServiceImpl;
import com.warehouse.loginservice.service.RefreshTokenService;
import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import com.warehouse.loginservice.dto.RefreshTokenRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    RefreshTokenService refreshTokenService;

    JWTServiceImpl jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user, null); // Default expiration (30 minutes)

        return ResponseEntity.ok(JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .userRole(user.getRole().name())
                .build());
    }

}
