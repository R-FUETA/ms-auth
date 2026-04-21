package duoc.fs3.ms_auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duoc.fs3.ms_auth.dto.request.LoginRequest;
import duoc.fs3.ms_auth.dto.request.RegisterRequest;
import duoc.fs3.ms_auth.model.User;
import duoc.fs3.ms_auth.repository.UserRepository;
import duoc.fs3.ms_auth.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Clase de pruebas unitarias para el AuthController.
 * 
 * Esta clase contiene las pruebas unitarias para los endpoints de autenticación
 * utilizando JUnit 5 y Mockito. Se prueba el registro de usuarios y el inicio de sesión.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    /**
     * Configuración inicial para cada prueba.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Prueba el registro exitoso de un nuevo usuario.
     */
    @Test
    @DisplayName("Debería registrar un usuario exitosamente")
    void testRegisterSuccess() throws Exception {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("Password123!");
        request.setConfirmPassword("Password123!");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("Password123!")).thenReturn("encodedPassword");

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado correctamente"));

        verify(userRepository).save(any(User.class));
    }

    /**
     * Prueba el registro con un nombre de usuario ya existente.
     */
    @Test
    @DisplayName("Debería fallar registro cuando username ya existe")
    void testRegisterWithExistingUsername() throws Exception {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");
        request.setEmail("test@example.com");
        request.setPassword("Password123!");
        request.setConfirmPassword("Password123!");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El nombre de usuario ya está en uso"));
    }

    /**
     * Prueba el registro con un email ya existente.
     */
    @Test
    @DisplayName("Debería fallar registro cuando email ya existe")
    void testRegisterWithExistingEmail() throws Exception {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("existing@example.com");
        request.setPassword("Password123!");
        request.setConfirmPassword("Password123!");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El correo electrónico ya está en uso"));
    }

    /**
     * Prueba el inicio de sesión exitoso.
     */
    @Test
    @DisplayName("Debería iniciar sesión exitosamente")
    void testLoginSuccess() throws Exception {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("testuser");
        request.setPassword("Password123!");

        when(jwtService.generateToken("testuser")).thenReturn("jwt-token");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso"));

        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken("testuser");
    }

    /**
     * Prueba el inicio de sesión con credenciales inválidas.
     */
    @Test
    @DisplayName("Debería fallar login con credenciales inválidas")
    void testLoginWithInvalidCredentials() throws Exception {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("testuser");
        request.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"));

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Credenciales inválidas"));
    }
}
