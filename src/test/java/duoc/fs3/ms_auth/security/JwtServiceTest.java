package duoc.fs3.ms_auth.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para el JwtService.
 * 
 * Esta clase contiene las pruebas unitarias para los métodos de generación
 * y extracción de tokens JWT utilizando JUnit 5.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    /**
     * Configuración inicial para cada prueba.
     */
    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    /**
     * Prueba la generación exitosa de un token JWT.
     */
    @Test
    @DisplayName("Debería generar un token JWT válido")
    void testGenerateToken() {
        // Given
        String username = "testuser";

        // When
        String token = jwtService.generateToken(username);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.length() > 50); // Los tokens JWT suelen ser largos
    }

    /**
     * Prueba la extracción correcta del username de un token válido.
     */
    @Test
    @DisplayName("Debería extraer el username correctamente de un token válido")
    void testExtractUsernameValidToken() {
        // Given
        String username = "testuser";
        String token = jwtService.generateToken(username);

        // When
        String extractedUsername = jwtService.extractUsername(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    /**
     * Prueba que diferentes usernames generan tokens diferentes.
     */
    @Test
    @DisplayName("Debería generar tokens diferentes para diferentes usuarios")
    void testDifferentUsersDifferentTokens() {
        // Given
        String username1 = "user1";
        String username2 = "user2";

        // When
        String token1 = jwtService.generateToken(username1);
        String token2 = jwtService.generateToken(username2);

        // Then
        assertNotEquals(token1, token2);
        assertEquals(username1, jwtService.extractUsername(token1));
        assertEquals(username2, jwtService.extractUsername(token2));
    }

    /**
     * Prueba que el mismo username genera tokens diferentes cada vez.
     */
    @Test
    @DisplayName("Debería generar tokens diferentes para el mismo usuario en llamadas distintas")
    void testSameUserDifferentTokens() {
        // Given
        String username = "testuser";

        // When
        String token1 = jwtService.generateToken(username);
        String token2 = jwtService.generateToken(username);

        // Then
        assertNotEquals(token1, token2); // Por el timestamp diferente
        assertEquals(username, jwtService.extractUsername(token1));
        assertEquals(username, jwtService.extractUsername(token2));
    }

    /**
     * Prueba que la clave getKey no sea nula.
     */
    @Test
    @DisplayName("Debería retornar una clave válida")
    void testGetKey() {
        // When
        var key = jwtService.getKey();

        // Then
        assertNotNull(key);
    }

    /**
     * Prueba la extracción de username con un token inválido.
     */
    @Test
    @DisplayName("Debería lanzar excepción con token inválido")
    void testExtractUsernameInvalidToken() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(io.jsonwebtoken.JwtException.class, () -> {
            jwtService.extractUsername(invalidToken);
        });
    }
}
