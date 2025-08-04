package com.example.linksharingappbackend.service;

import java.util.Optional;

import com.example.linksharingappbackend.entity.RefreshToken;

public interface RefreshTokenService {

    public RefreshToken createRefreshToken(String name);

    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken verifyExpiration(RefreshToken token);
}
