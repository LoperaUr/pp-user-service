package com.pragma.userservice.infrastructure.configuration;

import com.pragma.userservice.application.dto.UserDTO;
import com.pragma.userservice.application.handler.IUserHandler;
import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.spi.IRolePersistencePort;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class DefaultDevelopmentRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDevelopmentRunner.class);

    private final IUserHandler userHandler;
    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;

    @Override
    public void run(ApplicationArguments args) {
        initializeRoles();
        createDefaultOwner();
        createDefaultAdmin();
    }

    private void initializeRoles() {
        for (Role role : Role.values()) {
            try {
                rolePersistencePort.saveRole(role);
            } catch (Exception e) {
                logger.error("Failed to initialize role {}: {}", role.name(), e.getMessage(), e);
            }
        }
    }

    private void createDefaultAdmin() {
        createDefaultUser(
                "admin@example.com",
                "3001234565",
                "Admin",
                userHandler::createAdmin,
                "ADMIN"
        );
    }

    private void createDefaultOwner() {
        createDefaultUser(
                "owner@example.com",
                "3001234567",
                "Owner",
                userHandler::createOwner,
                "OWNER"
        );
    }

    private void createDefaultUser(
            String email,
            String phone,
            String firstName,
            Consumer<UserDTO> creator,
            String roleLabel
    ) {
        try {
            if (userPersistencePort.findByEmail(email).isPresent()) {
                return;
            }
            if (userPersistencePort.findByPhoneNumber(phone).isPresent()) {
                return;
            }

            UserDTO user = UserDTO.builder()
                    .name(firstName)
                    .lastName("Sample")
                    .identificationNumber("12345678")
                    .phoneNumber(phone)
                    .birthDate(LocalDate.parse("1990-05-20"))
                    .email(email)
                    .password("Secret123!")
                    .build();

            creator.accept(user);
        } catch (Exception e) {
            logger.error("Failed to create default {}: {}", roleLabel, e.getMessage(), e);
        }
    }
}
