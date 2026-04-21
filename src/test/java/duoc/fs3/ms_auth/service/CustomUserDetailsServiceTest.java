package duoc.fs3.ms_auth.service;

import duoc.fs3.ms_auth.model.User;
import duoc.fs3.ms_auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para el CustomUserDetailsService.
 * 
 * Esta clase contiene las pruebas unitarias para el servicio de detalles
 * de usuario personalizado utilizando JUnit 5 y Mockito.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User testUser;

    /**
     * Configuración inicial para cada prueba.
     */
    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .idUser(1L)
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Prueba la carga exitosa de un usuario por username.
     */
    @Test
    @DisplayName("Debería cargar usuario por username exitosamente")
    void testLoadUserByUsernameSuccess() {
        // Given
        String username = "testuser";
        when(userRepository.findByUsernameOrEmail(username, username))
                .thenReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(userRepository).findByUsernameOrEmail(username, username);
    }

    /**
     * Prueba la carga exitosa de un usuario por email.
     */
    @Test
    @DisplayName("Debería cargar usuario por email exitosamente")
    void testLoadUserByEmailSuccess() {
        // Given
        String email = "test@example.com";
        when(userRepository.findByUsernameOrEmail(email, email))
                .thenReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Then
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());

        verify(userRepository).findByUsernameOrEmail(email, email);
    }

    /**
     * Prueba el intento de carga de un usuario que no existe.
     */
    @Test
    @DisplayName("Debería lanzar UsernameNotFoundException cuando usuario no existe")
    void testLoadUserByUsernameNotFound() {
        // Given
        String username = "nonexistent";
        when(userRepository.findByUsernameOrEmail(username, username))
                .thenReturn(Optional.empty());

        // When & Then
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository).findByUsernameOrEmail(username, username);
    }

    /**
     * Prueba la carga de un usuario deshabilitado.
     */
    @Test
    @DisplayName("Debería cargar usuario deshabilitado correctamente")
    void testLoadDisabledUser() {
        // Given
        User disabledUser = User.builder()
                .idUser(2L)
                .username("disableduser")
                .email("disabled@example.com")
                .password("encodedPassword")
                .enabled(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userRepository.findByUsernameOrEmail("disableduser", "disableduser"))
                .thenReturn(Optional.of(disabledUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("disableduser");

        // Then
        assertNotNull(userDetails);
        assertEquals("disableduser", userDetails.getUsername());
        assertFalse(userDetails.isEnabled()); // El usuario debe estar deshabilitado
    }

    /**
     * Prueba que el UserDetails retornado tenga las autoridades correctas.
     */
    @Test
    @DisplayName("Debería asignar correctamente las autoridades ROLE_USER")
    void testUserAuthorities() {
        // Given
        when(userRepository.findByUsernameOrEmail("testuser", "testuser"))
                .thenReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Then
        assertNotNull(userDetails.getAuthorities());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }
}
