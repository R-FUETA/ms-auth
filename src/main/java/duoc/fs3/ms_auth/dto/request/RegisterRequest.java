package duoc.fs3.ms_auth.dto.request;

import duoc.fs3.ms_auth.validation.PasswordMatches;
import duoc.fs3.ms_auth.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la solicitud de registro de usuarios.
 * 
 * Esta clase contiene los datos necesarios para registrar un nuevo usuario
 * en el sistema. Incluye validaciones para asegurar la calidad de los datos
 * ingresados mediante anotaciones de Jakarta Validation.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class RegisterRequest {

    /**
     * Nombre de usuario para el registro.
     * Debe tener entre 4 y 30 caracteres y solo puede contener
     * letras, números y guion bajo.
     */
    @NotBlank(message = "{auth.username.notblank}")
    @Size(min = 4, max = 30, message = "{auth.username.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "{auth.username.pattern}")
    private String username;

    /**
     * Correo electrónico del usuario.
     * Debe ser una dirección de correo válida.
     */
    @NotBlank(message = "{auth.email.notblank}")
    @Email(message = "{auth.email.invalid}")
    private String email;

    /**
     * Contraseña del usuario.
     * Debe cumplir con las políticas de seguridad configuradas.
     */
    @NotBlank(message = "{auth.password.notblank}")
    @ValidPassword
    private String password;

    /**
     * Confirmación de la contraseña.
     * Debe ser idéntica al campo password.
     */
    @NotBlank(message = "{auth.confirmPassword.notblank}")
    private String confirmPassword;
}
