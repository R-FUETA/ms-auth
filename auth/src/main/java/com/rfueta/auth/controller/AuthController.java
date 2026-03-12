package com.rfueta.auth.controller;

import com.rfueta.auth.dto.request.RegisterRequest;
import com.rfueta.auth.model.Role;
import com.rfueta.auth.model.User;
import com.rfueta.auth.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.rfueta.auth.model.UserPublic;
import com.rfueta.auth.repository.UserPublicRepository;
import java.util.List;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final UserPublicRepository userPublicRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserPublicRepository userPublicRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userPublicRepository = userPublicRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserPublic>> listUsers() {
        return ResponseEntity.ok(userPublicRepository.findAll());
    }
}