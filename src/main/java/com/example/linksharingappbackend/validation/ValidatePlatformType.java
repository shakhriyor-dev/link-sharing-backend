package com.example.linksharingappbackend.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PlatformTypeValidator.class)
public @interface ValidatePlatformType {

    public String message() default "Invalid platform: Github, Youtube, Facebook, Instagram, Twitter or LinkedIn only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
