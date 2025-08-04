package com.example.linksharingappbackend.constant;

import org.springframework.http.HttpStatusCode;

public class HttpStatusConstants {
    public static final HttpStatusCode UNAUTHORIZED = HttpStatusCode.valueOf(401);
    public static final HttpStatusCode FORBIDDEN = HttpStatusCode.valueOf(403);

    public static final String AUTHENTICATION_FAILURE = "Authentication Failure";
    public static final String NOT_AUTHORIZED = "Not authorized!";
    public static final String JWT_SIGNATURE_NOT_VALID = "JWT Signature not valid";
    public static final String JWT_TOKEN_EXPIRED = "JWT Token expired";
    public static final String REFRESH_TOKEN_INVALID = "Refresh Token invalid";

    // Private constructor to prevent instantiation 
    private HttpStatusConstants() { 
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated"); 
    }
}
