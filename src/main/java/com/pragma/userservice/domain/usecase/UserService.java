package com.pragma.userservice.domain.usecase;

import com.pragma.userservice.domain.api.IPasswordServicePort;
import com.pragma.userservice.domain.api.ITokenServicePort;
import com.pragma.userservice.domain.api.IUserServicePort;
import com.pragma.userservice.domain.model.Auth;
import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import com.pragma.userservice.domain.constants.DomainConstants;

import java.time.LocalDate;
import java.time.Period;

import static com.pragma.userservice.infraestructure.util.UserValidator.validateCellphone;
import static com.pragma.userservice.infraestructure.util.UserValidator.validateDocument;

public record UserService(IUserPersistencePort userPersistencePort,
                          IPasswordServicePort passwordServicePort,
                          ITokenServicePort tokenServicePort
                          ) implements IUserServicePort {

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
                .orElseThrow(() -> new IllegalArgumentException(DomainConstants.MSG_USER_NOT_FOUND));
        user.setPassword(null);
        return user;
    }

    @Override
    public Auth login(Auth auth) {
        User user = userPersistencePort.findByEmail(auth.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(DomainConstants.MSG_USER_NOT_FOUND));

        if (!passwordServicePort.matches(auth.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException(DomainConstants.MSG_INVALID_CREDENTIALS);
        }

        return setTokenProperties(auth, user);
    }

    private Auth setTokenProperties(Auth auth, User user) {
        auth.setToken(tokenServicePort.generateToken(user.getEmail()));
        auth.setPassword(null);
        auth.setEmail(null);
        return auth;
    }

    private void validateUser(User userEntity, boolean validateAge) {
        if (!validateCellphone(userEntity.getPhoneNumber())) {
            throw new IllegalArgumentException(DomainConstants.MSG_INVALID_CELLPHONE);
        }
        if (!validateDocument(userEntity.getIdentificationNumber())) {
            throw new IllegalArgumentException(DomainConstants.MSG_INVALID_DOCUMENT);
        }
        if (validateAge && Period.between(userEntity.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException(DomainConstants.MSG_UNDERAGE_USER);
        }
        if (userPersistencePort.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new IllegalArgumentException(DomainConstants.MSG_EMAIL_ALREADY_EXISTS);
        }
        if (userPersistencePort.findByPhoneNumber(userEntity.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException(DomainConstants.MSG_PHONE_ALREADY_EXISTS);
        }
    }
}
