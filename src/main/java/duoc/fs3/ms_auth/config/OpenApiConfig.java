package duoc.fs3.ms_auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para OpenAPI/Swagger.
 * 
 * Esta clase configura la documentación de la API utilizando OpenAPI 3.0.
 * Define la información general de la API, esquemas de seguridad y
 * configuración de la interfaz de usuario de Swagger.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura el bean OpenAPI con la información de la API.
     * 
     * @return Configuración de OpenAPI para la documentación
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    /**
     * Crea la información general de la API.
     * 
     * @return Objeto Info con los datos de la API
     */
    private Info apiInfo() {
        return new Info()
                .title("Microservicio de Autenticación API")
                .description("API REST para la gestión de autenticación de usuarios. " +
                        "Proporciona endpoints para registro de usuarios y autenticación mediante tokens JWT.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Duoc UC Fullstack III")
                        .email("contacto@duoc.cl")
                        .url("https://www.duoc.cl"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
    }

    /**
     * Crea el esquema de seguridad para autenticación Bearer.
     * 
     * @return SecurityScheme configurado para JWT Bearer tokens
     */
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Por favor ingrese el token JWT en formato: Bearer <token>");
    }
}
