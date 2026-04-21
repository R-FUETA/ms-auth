package duoc.fs3.ms_auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación de validación personalizada para contraseñas.
 * 
 * Esta anotación se utiliza para validar que una contraseña cumpla
 * con las políticas de seguridad configuradas en la aplicación.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    /**
     * Mensaje de error por defecto cuando la validación falla.
     * 
     * @return Mensaje de error por defecto
     */
    String message() default "Contraseña inválida";

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
