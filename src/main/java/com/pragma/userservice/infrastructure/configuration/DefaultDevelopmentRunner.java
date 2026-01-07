package com.pragma.userservice.infrastructure.configuration;

import com.pragma.userservice.application.dto.UserDTO;
import com.pragma.userservice.application.handler.IUserHandler;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DefaultDevelopmentRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDevelopmentRunner.class);

    private final IUserHandler userHandler;
    private final IUserPersistencePort userPersistencePort;

    @Override
    public void run(ApplicationArguments args) {
        String email = "carlos.perez@example.com";
        String phone = "3001234567";

        if (userPersistencePort.findByEmail(email).isPresent()) {
            logger.info("Default owner already exists (email={}) - skipping creation", email);
            return;
        }
        if (userPersistencePort.findByPhoneNumber(phone).isPresent()) {
            logger.info("Default owner already exists (phone={}) - skipping creation", phone);
            return;
        }

        UserDTO user = UserDTO.builder()
                .name("Carlos")
                .lastName("Pérez")
                .identificationNumber("12345678")
                .phoneNumber(phone)
                .birthDate(LocalDate.parse("1990-05-20"))
                .email(email)
                .password("Secret123!")
                .build();

        try {
            userHandler.createOwner(user);
            logger.info("Default owner created ({})", email);
        } catch (Exception e) {
            logger.error("Failed to create default owner: {}", e.getMessage(), e);
        }
    }
}

