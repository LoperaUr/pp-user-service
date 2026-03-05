package com.pragma.userservice.infrastructure.output.jpa.adapter;

import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.spi.IRolePersistencePort;
import com.pragma.userservice.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.userservice.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;

    @Override
    public Optional<Long> findRoleIdByName(Role role) {
        return roleRepository.findByName(role).map(RoleEntity::getId);
    }

    @Override
    public void saveRole(Role role) {
        if (roleRepository.findByName(role).isEmpty()) {
            RoleEntity entity = RoleEntity.builder().name(role).build();
            roleRepository.save(entity);
        }
    }
}

