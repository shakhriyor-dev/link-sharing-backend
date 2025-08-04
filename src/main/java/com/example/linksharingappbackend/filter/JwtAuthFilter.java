package com.example.linksharingappbackend.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.linksharingappbackend.dto.UserPrincipal;
import com.example.linksharingappbackend.service.JwtService;
import com.example.linksharingappbackend.service.UserPrincipalService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserPrincipalService userPrincipalService;
    private final HandlerExceptionResolver exceptionResolver;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, UserPrincipalService userPrincipalService, HandlerExceptionResolver exceptionResolver) {
        this.jwtService = jwtService;
        this.userPrincipalService = userPrincipalService;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                email = jwtService.extractUsername(token);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserPrincipal userPrincipal = userPrincipalService.loadUserByUsername(email);
                if (jwtService.validateToken(token, userPrincipal)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userPrincipal, null, userPrincipal.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | SecurityException ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
