package com.p3h.realabs.eln.repository;

import com.p3h.realabs.eln.model.RoleEntity;
import com.p3h.realabs.eln.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findRoleEntitiesByName(String name);
}
