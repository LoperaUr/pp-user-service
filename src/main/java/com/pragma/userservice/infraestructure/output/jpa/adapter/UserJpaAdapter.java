package com.pragma.userservice.infraestructure.output.jpa.adapter;

import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import com.pragma.userservice.infraestructure.output.jpa.entity.UserEntity;
import com.pragma.userservice.infraestructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.userservice.infraestructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void saveUser(User user, String role) {
        UserEntity entity = userEntityMapper.toEntity(user);
        entity.setRole(role);
        userRepository.save(entity);
    }
}
