package com.czechp.DistillationServiceBackend.validator.annotation;

import com.czechp.DistillationServiceBackend.validator.implementation.PasswordValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented()
@Constraint(validatedBy = PasswordValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {
    String message() default "Invalid password format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
