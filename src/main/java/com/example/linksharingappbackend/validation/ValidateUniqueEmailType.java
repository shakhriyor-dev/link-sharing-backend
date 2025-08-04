package com.example.linksharingappbackend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface ValidateUniqueEmailType {
    String message() default "Email address already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
