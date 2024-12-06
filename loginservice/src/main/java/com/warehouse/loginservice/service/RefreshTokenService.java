package com.warehouse.loginservice.service;

import com.warehouse.loginservice.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {

    RefreshToken generateRefreshToken(String username);

    RefreshToken verifyRefreshToken(String refreshToken);

}
