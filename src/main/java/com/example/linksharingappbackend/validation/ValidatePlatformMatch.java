package com.example.linksharingappbackend.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PlatformMatchValidator.class)
public @interface ValidatePlatformMatch {

    public String message() default "Platform & URL must match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
