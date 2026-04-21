package duoc.fs3.ms_auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Servicio para la gestión de tokens JWT.
 * 
 * Esta clase proporciona métodos para generar, validar y extraer información
 * de tokens JSON Web Token (JWT). Utiliza una clave secreta generada
 * automáticamente para firmar los tokens.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Service
public class JwtService {

    /**
     * Clave secreta utilizada para firmar los tokens JWT.
     * Se genera automáticamente utilizando el algoritmo HS256.
     */
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Genera un token JWT para el usuario especificado.
     * 
     * @param username Nombre de usuario para el cual se genera el token
     * @return Token JWT con validez de 1 hora
     */
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(key)
                .compact();
    }

    /**
     * Extrae el nombre de usuario de un token JWT.
     * 
     * @param token Token JWT del cual se extrae el username
     * @return Nombre de usuario contenido en el token
     * @throws io.jsonwebtoken.JwtException Si el token es inválido
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Obtiene la clave secreta utilizada para firmar tokens.
     * 
     * @return Clave secreta para firmar tokens JWT
     */
    public Key getKey() {
        return key;
    }
}
