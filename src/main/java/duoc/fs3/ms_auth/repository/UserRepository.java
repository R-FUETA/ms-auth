package duoc.fs3.ms_auth.repository;

import duoc.fs3.ms_auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad User.
 * 
 * Esta interfaz proporciona métodos para realizar operaciones CRUD
 * sobre la entidad User y consultas personalizadas para buscar usuarios
 * por username, email o ambos.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario a buscar
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico a buscar
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca un usuario por nombre de usuario o correo electrónico.
     * 
     * @param username Nombre de usuario a buscar
     * @param email Correo electrónico a buscar
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Verifica si existe un usuario con el nombre de usuario especificado.
     * 
     * @param username Nombre de usuario a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un usuario con el correo electrónico especificado.
     * 
     * @param email Correo electrónico a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);
}
