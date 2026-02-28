package com.rfueta.auth.validation;

import com.rfueta.auth.config.PasswordSecurityProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final PasswordSecurityProperties passwordSecurityProperties;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int length = value.length();

        if (length < passwordSecurityProperties.getMinLength()
                || length > passwordSecurityProperties.getMaxLength()) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.length}").addConstraintViolation();

            return false;
        }
        
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        if (passwordSecurityProperties.isRequireUppercase() && !hasUpper) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.uppercase}").addConstraintViolation();
            return false;
        }

        if (passwordSecurityProperties.isRequireLowercase() && !hasLower) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.lowercase}").addConstraintViolation();
            return false;
        }

        if (passwordSecurityProperties.isRequireDigit() && !hasDigit) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.digit}").addConstraintViolation();
            return false;
        }

        if (passwordSecurityProperties.isRequireSpecial() && !hasSpecial) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.special}").addConstraintViolation();
            return false;
        }
        return true;
    }
}