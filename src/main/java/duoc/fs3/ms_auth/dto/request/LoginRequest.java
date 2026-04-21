package duoc.fs3.ms_auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la solicitud de inicio de sesión.
 * 
 * Esta clase contiene los datos necesarios para autenticar un usuario
 * en el sistema. Permite el inicio de sesión utilizando nombre de usuario
 * o correo electrónico.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * Nombre de usuario o correo electrónico del usuario.
     * Puede ser el username o el email registrados en el sistema.
     */
    @NotBlank(message = "El usuario o correo es obligatorio")
    private String usernameOrEmail;

    /**
     * Contraseña del usuario.
     * Debe coincidir con la contraseña almacenada en el sistema.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
