package duoc.fs3.ms_auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * Servicio para la gestión de tokens JWT.
 * 
 * Esta clase proporciona métodos para generar, validar y extraer información
 * de tokens JSON Web Token (JWT). Utiliza una clave secreta fija
 * configurada desde application.properties para garantizar consistencia
 * en entornos escalados como Docker.
 * 
 * @author Duoc UC Fullstack III
 * @version 1.0
 * @since 2026
 */
@Service
public class JwtService {

    // Constantes para configuración JWT
    private static final String DEFAULT_SECRET = "myDefaultSecretKeyThatMustBeAtLeast256BitsLongForHS256Algorithm";
    private static final long TOKEN_VALIDITY_MS = 3600000; // 1 hora en milisegundos

    /**
     * Clave secreta utilizada para firmar los tokens JWT.
     * Se obtiene desde application.properties para garantizar
     * que sea la misma en todas las instancias.
     */
    @Value("${jwt.secret:" + DEFAULT_SECRET + "}")
    private String jwtSecret;

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
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_MS))
                .signWith(getSigningKey())
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
                .setSigningKey(getSigningKey())
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
    public SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
