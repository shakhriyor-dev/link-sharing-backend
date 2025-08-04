package com.example.linksharingappbackend.service;

import java.util.Date;

import com.example.linksharingappbackend.dto.UserPrincipal;

public interface JwtService {

    public String extractUsername(String token);

    public Date extractExpiration(String token);

    public Boolean validateToken(String token, UserPrincipal userPrincipal);

    public String generateToken(String email);

}
