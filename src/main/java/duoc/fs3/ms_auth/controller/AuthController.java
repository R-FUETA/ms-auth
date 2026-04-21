package duoc.fs3.ms_auth.controller;

import duoc.fs3.ms_auth.dto.request.LoginRequest;
import duoc.fs3.ms_auth.dto.request.RegisterRequest;
import duoc.fs3.ms_auth.dto.response.LoginResponse;
import duoc.fs3.ms_auth.model.User;
import duoc.fs3.ms_auth.repository.UserRepository;
import duoc.fs3.ms_auth.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Controlador REST para la autenticación de usuarios.
 * 
 * Esta clase proporciona los endpoints para el registro de nuevos usuarios
 * y el inicio de sesión. Utiliza Spring Security para la autenticación
 * y JWT para la gestión de sesiones.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para la gestión de autenticación de usuarios")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Constructor que inyecta las dependencias necesarias.
     * 
     * @param userRepository Repositorio de usuarios
     * @param passwordEncoder Codificador de contraseñas
     * @param authenticationManager Gestor de autenticación
     * @param jwtService Servicio de gestión JWT
     */
    public AuthController(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         AuthenticationManager authenticationManager,
                         JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param request Datos de registro del usuario
     * @return Mensaje de confirmación del registro
     */
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de inválidos o usuario ya existe"),
        @ApiResponse(responseCode = "422", description = "Error de validación en los datos de entrada")
    })
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está en uso");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    /**
     * Inicia sesión de un usuario y genera un token JWT.
     * 
     * @param request Credenciales de inicio de sesión
     * @return Token JWT y información de la sesión
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y genera un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "400", description = "Error de validación en los datos de entrada")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()));

            String token = jwtService.generateToken(request.getUsernameOrEmail());

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest()
                    .body(LoginResponse.builder()
                            .message("Credenciales inválidas")
                            .build());
        }
    }
}
