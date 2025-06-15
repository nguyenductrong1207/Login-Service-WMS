package com.warehouse.loginservice.service;

import com.warehouse.loginservice.dto.LoginRequest;
import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import com.warehouse.loginservice.dto.request.IntrospectRequest;
import com.warehouse.loginservice.dto.response.IntrospectResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    JwtAuthenticationResponse login(LoginRequest loginRequest);

    IntrospectResponse introspect(IntrospectRequest request);
}