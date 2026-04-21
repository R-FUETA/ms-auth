package duoc.fs3.ms_auth.validation;

import duoc.fs3.ms_auth.config.PasswordSecurityProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validador personalizado para contraseñas.
 * 
 * Esta clase implementa la lógica de validación para la anotación @ValidPassword.
 * Verifica que la contraseña cumpla con las políticas de seguridad configuradas
 * en la aplicación (longitud, mayúsculas, minúsculas, números, caracteres especiales).
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Component
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final PasswordSecurityProperties passwordSecurityProperties;

    /**
     * Valida si la contraseña cumple con las políticas de seguridad.
     * 
     * @param value Contraseña a validar
     * @param context Contexto de validación
     * @return true si la contraseña es válida, false en caso contrario
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int length = value.length();

        // Validar longitud
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

        // Verificar composición de la contraseña
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

        // Validar mayúsculas
        if (passwordSecurityProperties.isRequireUppercase() && !hasUpper) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.uppercase}").addConstraintViolation();
            return false;
        }

        // Validar minúsculas
        if (passwordSecurityProperties.isRequireLowercase() && !hasLower) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.lowercase}").addConstraintViolation();
            return false;
        }

        // Validar números
        if (passwordSecurityProperties.isRequireDigit() && !hasDigit) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.digit}").addConstraintViolation();
            return false;
        }

        // Validar caracteres especiales
        if (passwordSecurityProperties.isRequireSpecial() && !hasSpecial) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{auth.password.special}").addConstraintViolation();
            return false;
        }
        
        return true;
    }
}
