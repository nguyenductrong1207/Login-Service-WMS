package com.warehouse.loginservice.controller;

import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import com.warehouse.loginservice.dto.LoginRequest;
import com.warehouse.loginservice.dto.RefreshTokenRequest;
import com.warehouse.loginservice.dto.SignUpRequest;
import com.warehouse.loginservice.entity.User;
import com.warehouse.loginservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup/manager")
    public ResponseEntity<User> signupForManager(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signupForManager(signUpRequest));
    }

    @PostMapping("/signup/staff")
    public ResponseEntity<User> signupForStaff(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signupForStaff(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}
