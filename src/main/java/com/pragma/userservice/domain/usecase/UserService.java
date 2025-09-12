package com.pragma.userservice.domain.usecase;

import com.pragma.userservice.domain.api.IPasswordServicePort;
import com.pragma.userservice.domain.api.IUserServicePort;
import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

import static com.pragma.userservice.infraestructure.util.UserValidator.validateCellphone;
import static com.pragma.userservice.infraestructure.util.UserValidator.validateDocument;

@RequiredArgsConstructor
public class UserService implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordServicePort passwordServicePort;

    @Override
    public void createOwner(User userEntity) {
        userEntity.setPassword(passwordServicePort.encodePassword(userEntity.getPassword()));
        validateUser(userEntity, true);
        userPersistencePort.saveUser(userEntity, Role.OWNER);
    }

    @Override
    public void createEmployee(User userEntity) {
        userEntity.setPassword(passwordServicePort.encodePassword(userEntity.getPassword()));
        validateUser(userEntity, false);
        userPersistencePort.saveUser(userEntity, Role.EMPLOYEE);
    }

    @Override
    public void createClient(User userEntity) {
        userEntity.setPassword(passwordServicePort.encodePassword(userEntity.getPassword()));
        validateUser(userEntity, false);
        userPersistencePort.saveUser(userEntity, Role.CLIENT);
    }

    @Override
    public User getUserById(Long id) {
        User user = userPersistencePort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(null);
        return user;
    }

    private void validateUser(User userEntity, boolean validateAge) {
        if (!validateCellphone(userEntity.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid cellphone number");
        }
        if (!validateDocument(userEntity.getIdentificationNumber())) {
            throw new IllegalArgumentException("Invalid document number");
        }
        if (validateAge && Period.between(userEntity.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException("User must be at least 18 years old");
        }
        if (userPersistencePort.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userPersistencePort.findByPhoneNumber(userEntity.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }
    }
}
