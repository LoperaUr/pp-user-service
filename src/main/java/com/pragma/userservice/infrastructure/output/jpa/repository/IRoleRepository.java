package com.pragma.userservice.infrastructure.output.jpa.repository;

import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(Role name);
}

