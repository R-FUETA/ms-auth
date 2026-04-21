package duoc.fs3.ms_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

/**
 * Clase de configuración de beans de seguridad.
 * 
 * Esta clase configura los beans necesarios para la seguridad de la aplicación,
 * incluyendo el codificador de contraseñas y el gestor de autenticación.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Configuration
public class SecurityBeansConfig {

    /**
     * Configura el codificador de contraseñas.
     * 
     * @return PasswordEncoder con BCrypt para codificar contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el gestor de autenticación.
     * 
     * @param config Configuración de autenticación
     * @return AuthenticationManager configurado
     * @throws Exception Si hay un error en la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
