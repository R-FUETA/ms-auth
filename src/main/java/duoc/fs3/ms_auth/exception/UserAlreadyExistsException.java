package duoc.fs3.ms_auth.exception;

/**
 * Excepción lanzada cuando un usuario ya existe en el sistema.
 * 
 * Esta excepción se utiliza para manejar casos específicos donde
 * un username o email ya está registrado, proporcionando un
 * manejo de errores más específico y controlado.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     * 
     * @param message Mensaje descriptivo del error
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * 
     * @param message Mensaje descriptivo del error
     * @param cause Causa original de la excepción
     */
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
