# ms-auth - Microservicio de Autenticación

Microservicio de autenticación de usuarios desarrollado con Spring Boot 3.5.13, Java 17 y Maven. Proporciona funcionalidades de registro e inicio de sesión mediante tokens JWT.

## Características

- **Registro de usuarios**: Creación de nuevas cuentas con validación de datos
- **Autenticación JWT**: Inicio de sesión con generación de tokens JSON Web Token
- **Validación de contraseñas**: Políticas de seguridad configurables para contraseñas
- **Arquitectura limpia**: Separación de responsabilidades con AuthService
- **Clave JWT fija**: Configurable para escalabilidad en Docker/AWS
- **Seguridad mejorada**: Headers HTTP seguros y configuración CORS
- **Documentación Swagger**: API documentada automáticamente con OpenAPI 3.0
- **Pruebas unitarias**: Cobertura de pruebas para los componentes principales
- **Base de datos MySQL**: Configurado para desarrollo local con Laragon

## Tecnologías

- **Java 17**
- **Spring Boot 3.5.13**
- **Spring Security** para autenticación y autorización
- **Spring Data JPA** para acceso a datos
- **MySQL** como base de datos
- **JWT (JSON Web Tokens)** para gestión de sesiones
- **SpringDoc OpenAPI** para documentación de API
- **JUnit 5** para pruebas unitarias
- **Lombok** para reducir código boilerplate

## Metadatos del Proyecto

- **Group**: duoc.fs3
- **Artifact**: ms-auth
- **Package**: duoc.fs3.ms_auth
- **Version**: 0.0.1-SNAPSHOT

## Requisitos Previos

- Java 17 o superior
- Maven 3.8+
- MySQL Server (configurado con Laragon para desarrollo local)
- IDE compatible con Java (IntelliJ IDEA, Eclipse, VS Code)

## Configuración de Base de Datos

1. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE auth_db;
   ```

2. La aplicación se conectará automáticamente a `localhost:3306/auth_db` con el usuario `root` y sin contraseña (configuración por defecto de Laragon).

## Ejecución de la Aplicación

### Desde Maven

```bash
# Clonar el repositorio (si aplica)
git clone <repository-url>
cd ms-auth

# Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

### Desde IDE

1. Importar el proyecto como proyecto Maven
2. Ejecutar la clase `MsAuthApplication.java`
3. La aplicación estará disponible en `http://localhost:8080`

## Endpoints de la API

### Autenticación

#### Registrar Usuario
- **POST** `/api/auth/register`
- **Body**:
  ```json
  {
    "username": "testuser",
    "email": "test@example.com",
    "password": "Password123!",
    "confirmPassword": "Password123!"
  }
  ```

#### Iniciar Sesión
- **POST** `/api/auth/login`
- **Body**:
  ```json
  {
    "usernameOrEmail": "testuser",
    "password": "Password123!"
  }
  ```
- **Response**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "message": "Inicio de sesión exitoso"
  }
  ```

## Documentación de la API

Una vez que la aplicación esté en ejecución, puedes acceder a:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Políticas de Contraseña

Las contraseñas deben cumplir con los siguientes requisitos:
- Longitud: 8-64 caracteres
- Al menos una letra mayúscula
- Al menos una letra minúscula
- Al menos un número
- Al menos un carácter especial

## Pruebas Unitarias

Para ejecutar las pruebas unitarias:

```bash
mvn test
```

Las pruebas cubren:
- Controlador de autenticación
- Servicio JWT
- Servicio de detalles de usuario
- Validadores de contraseña

## Configuración

### application.properties
Configuración principal de la aplicación incluyendo conexión a base de datos y políticas de seguridad.

### configuration.properties
Propiedades externas que pueden ser modificadas sin recompilar la aplicación.

## Estructura del Proyecto

```
src/main/java/duoc/fs3/ms_auth/
|-- MsAuthApplication.java          # Clase principal
|-- config/                         # Configuraciones
|   |-- SecurityConfig.java        # Configuración de seguridad
|   |-- PasswordSecurityProperties.java # Propiedades de contraseña
|   |-- SecurityBeansConfig.java   # Beans de seguridad
|   |-- OpenApiConfig.java          # Configuración Swagger/OpenAPI
|-- controller/
|   |-- AuthController.java         # Endpoints de autenticación (capa de presentación)
|-- dto/                            # Data Transfer Objects
|   |-- request/                    # DTOs de entrada
|   |-- response/                   # DTOs de salida
|-- model/
|   |-- User.java                   # Entidad de usuario
|   |-- UserPublic.java             # DTO público de usuario
|-- repository/
|   |-- UserRepository.java         # Repositorio JPA
|-- security/
|   |-- JwtService.java             # Servicio JWT (clave fija configurada)
|   |-- JwtAuthenticationFilter.java # Filtro JWT
|-- service/
|   |-- AuthService.java            # Servicio de autenticación (lógica de negocio)
|   |-- CustomUserDetailsService.java # Servicio de detalles de usuario
|-- validation/                     # Validadores personalizados
|   |-- PasswordValidator.java      # Validador de contraseñas
|   |-- PasswordMatches.java        # Anotación de validación
|   |-- PasswordMatchesValidator.java # Validador de coincidencias
```

## Arquitectura del Microservicio

### Separación de Responsabilidades
El microservicio sigue una arquitectura limpia con separación clara de responsabilidades:

- **Controller**: Manejo de solicitudes HTTP y respuestas
- **Service**: Lógica de negocio centralizada en `AuthService`
- **Repository**: Acceso a datos con Spring Data JPA
- **Security**: Configuración de seguridad y gestión JWT

### Configuración JWT
La clave JWT es fija y configurable para garantizar consistencia entre instancias:

```properties
# Clave secreta fija para firmar tokens JWT (Base64 encoded)
jwt.secret=bXlZGVmYXVsdEtleXRrZXNLXRlc3JldGlvbkVzZXJjZXRTZWNyZXRLZXk5b25zSGFzaTI1NkFsZ29yaXRobA==
```

### Ventajas de la Arquitectura
- **Escalabilidad**: Clave JWT fija para Docker y entornos en la nube
- **Mantenibilidad**: Lógica centralizada facilita cambios futuros
- **Testing**: Más fácil de probar componentes de forma aislada
- **Seguridad**: Configuración centralizada de políticas de seguridad

## Desarrollo

### Agregar nuevas funcionalidades

1. Crear las clases necesarias en los paquetes correspondientes
2. Agregar pruebas unitarias
3. Actualizar la documentación con anotaciones Swagger
4. Actualizar este README si es necesario

### Estándares de Código

- Todo el código está documentado con JavaDoc en español
- Se siguen las convenciones de nomenclatura de Java
- Las pruebas unitarias deben cubrir al menos los casos principales

## Licencia

MIT License - Ver archivo LICENSE para más detalles.

## Autor

Desarrollado por Duoc UC - Fullstack III (2026)
