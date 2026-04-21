package duoc.fs3.ms_auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Clase de propiedades de configuración para políticas de contraseña.
 * 
 * Esta clase permite configurar las políticas de seguridad para contraseñas
 * mediante el archivo application.properties. Utiliza @ConfigurationProperties
 * para mapear automáticamente las propiedades con el prefijo "security.password".
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "security.password")
public class PasswordSecurityProperties {
    
    /**
     * Longitud mínima requerida para la contraseña.
     */
    private int minLength;
    
    /**
     * Longitud máxima permitida para la contraseña.
     */
    private int maxLength;
    
    /**
     * Indica si se requiere al menos una letra mayúscula.
     */
    private boolean requireUppercase;
    
    /**
     * Indica si se requiere al menos una letra minúscula.
     */
    private boolean requireLowercase;
    
    /**
     * Indica si se requiere al menos un número.
     */
    private boolean requireDigit;
    
    /**
     * Indica si se requiere al menos un carácter especial.
     */
    private boolean requireSpecial;
}
