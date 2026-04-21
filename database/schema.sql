-- Esquema de base de datos para el microservicio de autenticación ms-auth
-- Compatible con MySQL 8.0+

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS auth_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE auth_db;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id_user BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices para mejorar el rendimiento
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_enabled (enabled),
    INDEX idx_created_at (created_at)
);

-- Insertar usuario de prueba (opcional)
-- Contraseña: TestPassword123!
INSERT IGNORE INTO users (username, email, password, enabled, created_at, updated_at)
VALUES (
    'testuser',
    'test@example.com',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', -- BCrypt de "TestPassword123!"
    TRUE,
    NOW(),
    NOW()
);

-- Mostrar la estructura de la tabla
DESCRIBE users;

-- Mostrar los usuarios existentes
SELECT * FROM users;
