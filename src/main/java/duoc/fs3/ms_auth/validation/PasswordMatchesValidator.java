package duoc.fs3.ms_auth.validation;

import duoc.fs3.ms_auth.dto.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Validador personalizado para confirmación de contraseña.
 * 
 * Esta clase implementa la lógica de validación para la anotación @PasswordMatches.
 * Compara los campos password y confirmPassword para asegurar que sean idénticos.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    /**
     * Inicializa el validador.
     * 
     * @param constraintAnnotation Anotación de constraint
     */
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    /**
     * Valida que las contraseñas coincidan.
     * 
     * @param obj Objeto que contiene los campos a validar
     * @param context Contexto de validación
     * @return true si las contraseñas coinciden, false en caso contrario
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        String password = (String) new BeanWrapperImpl(obj).getPropertyValue("password");
        String confirmPassword = (String) new BeanWrapperImpl(obj).getPropertyValue("confirmPassword");

        boolean isValid = password != null && password.equals(confirmPassword);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
