package com.janblog.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Size(min = 6, max = 128, message = "Password must be between 6 and 128 characters long")
@Constraint(validatedBy = PasswordValidator.class)
@Target({METHOD, FIELD, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Password {

    String message() default "Password must have ASCII characters only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
