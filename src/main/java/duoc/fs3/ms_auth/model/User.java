package duoc.fs3.ms_auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entidad de usuario que representa un usuario en el sistema.
 * 
 * Esta clase implementa la interfaz UserDetails de Spring Security para
 * integrarse con el sistema de autenticación. Almacena la información
 * básica del usuario necesaria para la autenticación.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    /**
     * Nombre de usuario único.
     */
    @Column(nullable = false, length = 30)
    private String username;

    /**
     * Correo electrónico único del usuario.
     */
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    /**
     * Contraseña codificada del usuario.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Indica si el usuario está habilitado o no.
     */
    @Column(nullable = false)
    private boolean enabled;

    /**
     * Fecha y hora de creación del usuario.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de última actualización del usuario.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ===== Métodos de Spring Security =====

    /**
     * Obtiene las autoridades (roles) del usuario.
     * 
     * @return Colección de autoridades del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Verifica si la cuenta del usuario no ha expirado.
     * 
     * @return true si la cuenta no ha expirado
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Verifica si la cuenta del usuario no está bloqueada.
     * 
     * @return true si la cuenta no está bloqueada
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Verifica si las credenciales del usuario no han expirado.
     * 
     * @return true si las credenciales no han expirado
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Verifica si el usuario está habilitado.
     * 
     * @return true si el usuario está habilitado
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
