package com.example.linksharingappbackend.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.linksharingappbackend.constant.HttpStatusConstants;
import com.example.linksharingappbackend.dto.ErrorDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex, HttpServletRequest request) {
        ProblemDetail errorDetail = null;
        if (ex instanceof BadCredentialsException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusConstants.UNAUTHORIZED, ex.getMessage());
            errorDetail.setProperty("access_denied_reason", HttpStatusConstants.AUTHENTICATION_FAILURE);
        }

        if (ex instanceof AccessDeniedException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusConstants.FORBIDDEN, ex.getMessage());
            errorDetail.setProperty("access_denied_reason", HttpStatusConstants.NOT_AUTHORIZED);

        }

        if (ex instanceof SignatureException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusConstants.FORBIDDEN, ex.getMessage());
            errorDetail.setProperty("access_denied_reason", HttpStatusConstants.JWT_SIGNATURE_NOT_VALID);
        }

        // it works better if you hit the refresh route without a bearer token vs an
        // expired token. An expired token results in null being sent back to the
        // frontend.
        if (ex instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusConstants.UNAUTHORIZED, ex.getMessage());
            errorDetail.setProperty("access_denied_reason", HttpStatusConstants.JWT_TOKEN_EXPIRED);
            // Could handle refresh token request here
        }

        if (ex instanceof InvalidTokenException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusConstants.UNAUTHORIZED, ex.getMessage());
            errorDetail.setProperty("access_denied_reason", HttpStatusConstants.REFRESH_TOKEN_INVALID);
        }

        if (ex instanceof SecurityException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusConstants.UNAUTHORIZED, ex.getMessage());
            errorDetail.setProperty("access_denied_reason", HttpStatusConstants.AUTHENTICATION_FAILURE);
        }

        return errorDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        List<ErrorDetails> errors = new ArrayList<>();

        if (exception.getBindingResult().hasErrors()) {
            exception.getBindingResult().getFieldErrors()
                    .forEach(error -> errors.add(new ErrorDetails(error.getField(), error.getDefaultMessage())));
        }

        return ResponseEntity.unprocessableEntity().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" " + violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occurred.";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EntityNotFoundException.class, UsernameNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
