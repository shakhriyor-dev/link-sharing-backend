package com.example.linksharingappbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.linksharingappbackend.dto.AuthRequest;
import com.example.linksharingappbackend.dto.JwtResponse;
import com.example.linksharingappbackend.dto.RefreshTokenRequest;
import com.example.linksharingappbackend.entity.RefreshToken;
import com.example.linksharingappbackend.exception.InvalidTokenException;
import com.example.linksharingappbackend.service.JwtService;
import com.example.linksharingappbackend.service.RefreshTokenServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class TokenController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    /**
     * @param authRequest
     * @return JwtResponse
     */
    @PostMapping("/authenticate")
    public JwtResponse authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        Authentication validUser = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (validUser.isAuthenticated()) {

            String email = authRequest.getEmail();

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(email))
                    .refreshToken(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    /**
     * @param refreshTokenRequest
     * @return ResponseEntity<JwtResponse>
     */
    // retuns null in the frontend -> Optional problems?
    // If expired JWT token is in header, the request will fail
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getEmail());
                    JwtResponse jwtResponse = JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken())
                            .build();
                    return ResponseEntity.status(200).body(jwtResponse);
                })
                .orElseThrow(() -> new InvalidTokenException("Refresh token invalid"));
    }

    /**
     * @param token
     * @return ResponseEntity<JwtResponse>
     */
    // duplicated the refresh to see if the response body was an issue -> it wasn't
    @GetMapping("/refresh2")
    public ResponseEntity<JwtResponse> refreshToken2(@RequestParam String token) {
        return refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getEmail());
                    JwtResponse jwtResponse = JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(token)
                            .build();
                    return ResponseEntity.status(200).body(jwtResponse);
                })
                .orElseThrow(() -> new InvalidTokenException("Refresh token invalid"));
    }
}
