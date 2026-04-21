package duoc.fs3.ms_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Clase principal de la aplicación de autenticación.
 * 
 * Esta clase contiene el método main que inicia el microservicio de autenticación
 * utilizando Spring Boot. Configura el escaneo de propiedades de configuración
 * para permitir la inyección de propiedades personalizadas.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class MsAuthApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(MsAuthApplication.class, args);
    }

}
