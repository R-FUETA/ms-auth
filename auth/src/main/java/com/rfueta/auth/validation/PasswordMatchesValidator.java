package com.rfueta.auth.validation;

import com.rfueta.auth.dto.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        boolean matches = value.getPassword() != null &&
                          value.getPassword().equals(value.getConfirmPassword());

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.mismatch}"
            ).addPropertyNode("confirmPassword")
             .addConstraintViolation();
        }

        return matches;
    }
}