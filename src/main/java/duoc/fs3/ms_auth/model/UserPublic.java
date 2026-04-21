package duoc.fs3.ms_auth.model;

import lombok.*;

/**
 * DTO público de usuario para respuestas API.
 * 
 * Esta clase representa la información pública de un usuario que puede ser
 * expuesta a través de los endpoints de la API sin incluir información sensible
 * como la contraseña.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPublic {

    /**
     * Identificador único del usuario.
     */
    private Long idUser;

    /**
     * Nombre de usuario.
     */
    private String username;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Indica si el usuario está habilitado.
     */
    private boolean enabled;

    /**
     * Fecha y hora de creación del usuario.
     */
    private String createdAt;
}
