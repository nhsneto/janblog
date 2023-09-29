package com.janblog.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters long")
@Constraint(validatedBy = PasswordValidator.class)
@Target({METHOD, FIELD, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Document
public @interface Password {

    String message() default "Password must have ASCII characters only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
