package com.warehouse.loginservice.service;

import com.warehouse.loginservice.dto.JwtAuthenticationResponse;
import com.warehouse.loginservice.dto.LoginRequest;
import com.warehouse.loginservice.dto.SignUpRequest;
import com.warehouse.loginservice.entity.User;

public interface AuthenticationService {

    User signupForManager(SignUpRequest signUpRequest);

    User signupForStaff(SignUpRequest signUpRequest);

    JwtAuthenticationResponse login(LoginRequest loginRequest);

}
