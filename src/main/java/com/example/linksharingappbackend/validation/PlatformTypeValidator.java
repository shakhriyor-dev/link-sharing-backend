package com.example.linksharingappbackend.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlatformTypeValidator implements ConstraintValidator<ValidatePlatformType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> platforms = Arrays.asList("github", "youtube", "facebook", "instagram", "twitter", "linkedin");
        return platforms.contains(value.toLowerCase());
    }
}