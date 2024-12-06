package com.warehouse.loginservice.service;

import com.warehouse.loginservice.dto.LoginRequest;
import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    JwtAuthenticationResponse login(LoginRequest loginRequest);

}