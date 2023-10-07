package com.janblog.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Size(min = 6, max = 30, message = "Username must be between 6 and 30 characters long")
@Constraint(validatedBy = UsernameValidator.class)
@Target({METHOD, FIELD, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Username {

    String message() default "Username must be alphanumeric and start with a letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
