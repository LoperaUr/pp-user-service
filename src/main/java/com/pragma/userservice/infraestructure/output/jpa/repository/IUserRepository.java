package com.pragma.userservice.infraestructure.output.jpa.repository;

import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.infraestructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
