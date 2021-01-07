package com.czechp.DistillationServiceBackend.validator.implementation;

import com.czechp.DistillationServiceBackend.validator.annotation.PasswordValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean lengthCorrect = s.length() >= 4 && s.length() <= 20;
        boolean hasDigit = hasDigit(s);
        boolean hasUpperCharacter = hasUpperCharacter(s);
        return lengthCorrect && hasDigit && hasUpperCharacter;
    }


    @Override
    public void initialize(PasswordValidator constraintAnnotation) {

    }

    private boolean hasUpperCharacter(String s) {
        for (char sign : s.toCharArray()) {
            if (Character.isUpperCase(sign)) return true;
        }
        return false;
    }

    private boolean hasDigit(String s) {
        for (char sign : s.toCharArray()) {
            if (Character.isDigit(sign)) return true;
        }
        return false;
    }
}
