package duoc.fs3.ms_auth.service;

import duoc.fs3.ms_auth.model.User;
import duoc.fs3.ms_auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado para cargar detalles de usuario.
 * 
 * Esta clase implementa la interfaz UserDetailsService de Spring Security
 * para cargar los detalles de un usuario desde la base de datos durante
 * el proceso de autenticación.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor que inyecta el repositorio de usuarios.
     * 
     * @param userRepository Repositorio para acceder a los datos de usuarios
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carga los detalles de un usuario por nombre de usuario o correo electrónico.
     * 
     * @param usernameOrEmail Nombre de usuario o correo electrónico a buscar
     * @return UserDetails con la información del usuario
     * @throws UsernameNotFoundException Si el usuario no es encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .disabled(!user.isEnabled())
                .build();
    }
}
