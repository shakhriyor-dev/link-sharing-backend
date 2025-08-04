package com.example.linksharingappbackend.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.linksharingappbackend.repository.UserInfoRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// either approach works if you add 
// spring.jpa.properties.javax.persistence.validation.mode=none
// to your application.properties file

@Component
public class UniqueEmailValidator implements ConstraintValidator<ValidateUniqueEmailType, String> {

    // without spring.jpa.properties.javax.persistence.validation.mode=none
    // userInfoRepository will be null in this validator

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userInfoRepository.findByEmail(value).isEmpty();
    }

}
