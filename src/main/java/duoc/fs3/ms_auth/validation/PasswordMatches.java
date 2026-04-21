package duoc.fs3.ms_auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación de validación para confirmación de contraseña.
 * 
 * Esta anotación se utiliza para validar que el campo de confirmación
 * de contraseña coincida con el campo de contraseña original.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {

    /**
     * Mensaje de error por defecto cuando las contraseñas no coinciden.
     * 
     * @return Mensaje de error por defecto
     */
    String message() default "{auth.password.mismatch}";

    /**
     * Grupos de validación a los que pertenece esta constraint.
     * 
     * @return Arreglo de grupos de validación
     */
    Class<?>[] groups() default {};

    /**
     * Payload para información adicional sobre la validación.
     * 
     * @return Payload de la validación
     */
    Class<? extends Payload>[] payload() default {};
}
