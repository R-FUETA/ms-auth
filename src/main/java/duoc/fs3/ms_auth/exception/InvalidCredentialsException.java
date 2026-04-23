package duoc.fs3.ms_auth.exception;

/**
 * Excepción lanzada cuando las credenciales de autenticación son inválidas.
 * 
 * Esta excepción se utiliza para manejar casos específicos de autenticación
 * fallida, proporcionando un manejo de errores más específico y controlado
 * que permite diferenciar entre diferentes tipos de errores de autenticación.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructor con mensaje de error.
     * 
     * @param message Mensaje descriptivo del error
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * 
     * @param message Mensaje descriptivo del error
     * @param cause Causa original de la excepción
     */
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
