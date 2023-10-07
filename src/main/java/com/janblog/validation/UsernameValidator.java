package com.janblog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        String validUsernameRegex = "^[a-zA-Z][a-zA-Z0-9]*$";
        return username == null || Pattern.compile(validUsernameRegex).matcher(username).matches();
    }
}
