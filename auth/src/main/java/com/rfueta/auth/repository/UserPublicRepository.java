package com.rfueta.auth.repository;

import com.rfueta.auth.model.UserPublic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPublicRepository extends JpaRepository<UserPublic, Long> {
}