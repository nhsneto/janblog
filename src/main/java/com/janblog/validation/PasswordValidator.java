package com.janblog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        String validEmailRegex = "^[\\p{ASCII}]*$";
        return password == null || Pattern.compile(validEmailRegex).matcher(password).matches();
    }
}
