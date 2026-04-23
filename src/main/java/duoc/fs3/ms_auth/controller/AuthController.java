package duoc.fs3.ms_auth.controller;

import duoc.fs3.ms_auth.dto.request.LoginRequest;
import duoc.fs3.ms_auth.dto.request.RegisterRequest;
import duoc.fs3.ms_auth.dto.response.LoginResponse;
import duoc.fs3.ms_auth.exception.InvalidCredentialsException;
import duoc.fs3.ms_auth.exception.UserAlreadyExistsException;
import duoc.fs3.ms_auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la autenticación de usuarios.
 * 
 * Esta clase proporciona los endpoints para el registro de nuevos usuarios
 * y el inicio de sesión. Delega la lógica de negocio al AuthService
 * para mantener una arquitectura limpia y escalable.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para la gestión de autenticación de usuarios")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor que inyecta el servicio de autenticación.
     * 
     * @param authService Servicio de autenticación con la lógica de negocio
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
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
        
        try {
            String result = authService.registerUser(request);
            return ResponseEntity.ok(result);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
            LoginResponse response = authService.loginUser(request);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(LoginResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }
}
