package com.janblog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String emailRegex = "^(?=.{4,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-\\.][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";
        return email == null || Pattern.compile(emailRegex).matcher(email).matches();
    }
}
