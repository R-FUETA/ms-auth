package com.rfueta.auth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS_PUBLIC_VIEW")
@Getter
@Setter
public class UserPublic {

    @Id
    @Column(name = "ID_USER")
    private Long idUser;

    private String username;
    private String email;
    private String role;
    private Boolean enabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}