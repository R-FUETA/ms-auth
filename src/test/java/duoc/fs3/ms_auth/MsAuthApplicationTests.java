package duoc.fs3.ms_auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Clase de pruebas de integración para la aplicación.
 * 
 * Esta prueba verifica que el contexto de la aplicación Spring Boot
 * se cargue correctamente sin errores.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@SpringBootTest
class MsAuthApplicationTests {

    /**
     * Prueba que el contexto de la aplicación se carga correctamente.
     * Esta es una prueba básica que asegura que todos los beans y configuraciones
     * se pueden inicializar sin problemas.
     */
    @Test
    @DisplayName("Debería cargar el contexto de la aplicación exitosamente")
    void contextLoads() {
        // Si esta prueba se ejecuta sin lanzar excepciones,
        // significa que el contexto de Spring se cargó correctamente
    }
}
