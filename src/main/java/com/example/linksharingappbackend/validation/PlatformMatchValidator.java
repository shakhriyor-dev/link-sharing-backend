package com.example.linksharingappbackend.validation;

import com.example.linksharingappbackend.entity.Link;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlatformMatchValidator implements ConstraintValidator<ValidatePlatformMatch, Link> {

    // Not perfect -> possible edge cases?
    /**
     * @param value
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Link value, ConstraintValidatorContext context) {
        String platform = value.getPlatform().toLowerCase();
        String url = value.getUrl().toLowerCase();
        return url.contains(platform + ".com");
    }

}
