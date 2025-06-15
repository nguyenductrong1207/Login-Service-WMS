package com.warehouse.loginservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface JWTService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails, Long expirationTime);

    boolean isTokenValid(String token, UserDetails userDetails);

    SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException;
}
