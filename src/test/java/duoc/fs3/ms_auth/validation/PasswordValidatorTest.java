package duoc.fs3.ms_auth.validation;

import duoc.fs3.ms_auth.config.PasswordSecurityProperties;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para el PasswordValidator.
 * 
 * Esta clase contiene las pruebas unitarias para el validador de contraseñas
 * utilizando JUnit 5 y Mockito.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    @Mock
    private PasswordSecurityProperties passwordSecurityProperties;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @InjectMocks
    private PasswordValidator passwordValidator;

    /**
     * Configuración inicial para cada prueba.
     */
    @BeforeEach
    void setUp() {
        // Configurar propiedades por defecto
        when(passwordSecurityProperties.getMinLength()).thenReturn(8);
        when(passwordSecurityProperties.getMaxLength()).thenReturn(64);
        when(passwordSecurityProperties.isRequireUppercase()).thenReturn(true);
        when(passwordSecurityProperties.isRequireLowercase()).thenReturn(true);
        when(passwordSecurityProperties.isRequireDigit()).thenReturn(true);
        when(passwordSecurityProperties.isRequireSpecial()).thenReturn(true);

        // Configurar mock del contexto
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
    }

    /**
     * Prueba una contraseña válida que cumple todos los requisitos.
     */
    @Test
    @DisplayName("Debería validar contraseña fuerte exitosamente")
    void testValidStrongPassword() {
        // Given
        String validPassword = "Password123!";

        // When
        boolean result = passwordValidator.isValid(validPassword, context);

        // Then
        assertTrue(result);
        verify(context, never()).disableDefaultConstraintViolation();
    }

    /**
     * Prueba una contraseña demasiado corta.
     */
    @Test
    @DisplayName("Debería fallar con contraseña demasiado corta")
    void testPasswordTooShort() {
        // Given
        String shortPassword = "Pass1!";
        when(passwordSecurityProperties.getMinLength()).thenReturn(8);

        // When
        boolean result = passwordValidator.isValid(shortPassword, context);

        // Then
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("{auth.password.length}");
    }

    /**
     * Prueba una contraseña demasiado larga.
     */
    @Test
    @DisplayName("Debería fallar con contraseña demasiado larga")
    void testPasswordTooLong() {
        // Given
        String longPassword = "ThisIsAVeryLongPassword123!ThatExceedsTheMaximumAllowedLength";
        when(passwordSecurityProperties.getMaxLength()).thenReturn(20);

        // When
        boolean result = passwordValidator.isValid(longPassword, context);

        // Then
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("{auth.password.length}");
    }

    /**
     * Prueba una contraseña sin mayúsculas.
     */
    @Test
    @DisplayName("Debería fallar cuando no hay mayúsculas")
    void testPasswordWithoutUppercase() {
        // Given
        String password = "password123!";
        when(passwordSecurityProperties.isRequireUppercase()).thenReturn(true);

        // When
        boolean result = passwordValidator.isValid(password, context);

        // Then
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("{auth.password.uppercase}");
    }

    /**
     * Prueba una contraseña sin minúsculas.
     */
    @Test
    @DisplayName("Debería fallar cuando no hay minúsculas")
    void testPasswordWithoutLowercase() {
        // Given
        String password = "PASSWORD123!";
        when(passwordSecurityProperties.isRequireLowercase()).thenReturn(true);

        // When
        boolean result = passwordValidator.isValid(password, context);

        // Then
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("{auth.password.lowercase}");
    }

    /**
     * Prueba una contraseña sin números.
     */
    @Test
    @DisplayName("Debería fallar cuando no hay números")
    void testPasswordWithoutDigits() {
        // Given
        String password = "Password!";
        when(passwordSecurityProperties.isRequireDigit()).thenReturn(true);

        // When
        boolean result = passwordValidator.isValid(password, context);

        // Then
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("{auth.password.digit}");
    }

    /**
     * Prueba una contraseña sin caracteres especiales.
     */
    @Test
    @DisplayName("Debería fallar cuando no hay caracteres especiales")
    void testPasswordWithoutSpecial() {
        // Given
        String password = "Password123";
        when(passwordSecurityProperties.isRequireSpecial()).thenReturn(true);

        // When
        boolean result = passwordValidator.isValid(password, context);

        // Then
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("{auth.password.special}");
    }

    /**
     * Prueba con valor nulo (debería ser válido).
     */
    @Test
    @DisplayName("Debería ser válido cuando el valor es nulo")
    void testNullPassword() {
        // Given
        String nullPassword = null;

        // When
        boolean result = passwordValidator.isValid(nullPassword, context);

        // Then
        assertTrue(result);
    }

    /**
     * Prueba cuando no se requieren mayúsculas.
     */
    @Test
    @DisplayName("Debería ser válido cuando no se requieren mayúsculas")
    void testPasswordWithoutUppercaseNotRequired() {
        // Given
        String password = "password123!";
        when(passwordSecurityProperties.isRequireUppercase()).thenReturn(false);

        // When
        boolean result = passwordValidator.isValid(password, context);

        // Then
        assertTrue(result);
    }

    /**
     * Prueba cuando no se requieren caracteres especiales.
     */
    @Test
    @DisplayName("Debería ser válido cuando no se requieren caracteres especiales")
    void testPasswordWithoutSpecialNotRequired() {
        // Given
        String password = "Password123";
        when(passwordSecurityProperties.isRequireSpecial()).thenReturn(false);

        // When
        boolean result = passwordValidator.isValid(password, context);

        // Then
        assertTrue(result);
    }
}
