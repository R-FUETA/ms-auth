package duoc.fs3.ms_auth.service;

import duoc.fs3.ms_auth.dto.request.LoginRequest;
import duoc.fs3.ms_auth.dto.request.RegisterRequest;
import duoc.fs3.ms_auth.dto.response.LoginResponse;
import duoc.fs3.ms_auth.exception.InvalidCredentialsException;
import duoc.fs3.ms_auth.exception.UserAlreadyExistsException;
import duoc.fs3.ms_auth.model.User;
import duoc.fs3.ms_auth.repository.UserRepository;
import duoc.fs3.ms_auth.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio de autenticación de usuarios.
 * 
 * Esta clase contiene la lógica de negocio para el registro e inicio de sesión
 * de usuarios. Separa la lógica del controlador para mantener una
 * arquitectura limpia y escalable.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

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
    public AuthService(UserRepository userRepository,
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
     * @throws UserAlreadyExistsException Si el usuario ya existe
     */
    @Transactional
    public String registerUser(RegisterRequest request) {
        
        logger.info("Intento de registro de usuario: {}", request.getUsername());
        
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("Intento de registro con username ya existente: {}", request.getUsername());
            throw new UserAlreadyExistsException("El nombre de usuario ya está en uso");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new UserAlreadyExistsException("El correo electrónico ya está en uso");
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
        
        logger.info("Usuario registrado exitosamente: {}", user.getUsername());
        return "Usuario registrado correctamente";
    }

    /**
     * Inicia sesión de un usuario y genera un token JWT.
     * 
     * @param request Credenciales de inicio de sesión
     * @return Respuesta con token JWT y mensaje de estado
     * @throws InvalidCredentialsException Si las credenciales son inválidas
     */
    public LoginResponse loginUser(LoginRequest request) {
        
        logger.info("Intento de login para usuario: {}", request.getUsernameOrEmail());
        
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()));

            String token = jwtService.generateToken(request.getUsernameOrEmail());
            
            logger.info("Login exitoso para usuario: {}", request.getUsernameOrEmail());
            return new LoginResponse(token);

        } catch (AuthenticationException e) {
            logger.warn("Login fallido para usuario: {} - {}", request.getUsernameOrEmail(), e.getMessage());
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
    }
}
