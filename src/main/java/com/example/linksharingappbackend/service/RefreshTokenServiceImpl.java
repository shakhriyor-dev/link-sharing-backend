package com.example.linksharingappbackend.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linksharingappbackend.entity.RefreshToken;
import com.example.linksharingappbackend.exception.InvalidTokenException;
import com.example.linksharingappbackend.repository.RefreshTokenRepository;
import com.example.linksharingappbackend.repository.UserInfoRepository;

@Service
public class RefreshTokenServiceImpl {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public RefreshToken createRefreshToken(String name) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfoRepository.findByEmail(name).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))// 10 minutes
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new InvalidTokenException(token.getToken() + " Refresh token is expired. Please sign in again.");
        }
        return token;
    }

}
