// src/main/java/com/p3h/realabs/eln/repository/UserRepository.java
package com.p3h.realabs.eln.repository;

import com.p3h.realabs.eln.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
