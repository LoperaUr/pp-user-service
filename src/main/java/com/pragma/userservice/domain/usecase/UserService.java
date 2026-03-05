package com.pragma.userservice.domain.usecase;

import com.pragma.userservice.domain.api.IPasswordServicePort;
import com.pragma.userservice.domain.api.ITokenServicePort;
import com.pragma.userservice.domain.api.IUserServicePort;
import com.pragma.userservice.domain.constants.DomainConstants;
import com.pragma.userservice.domain.exception.DomainException;
import com.pragma.userservice.domain.model.Auth;
import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

import static com.pragma.userservice.infrastructure.util.UserValidator.validateCellphone;
import static com.pragma.userservice.infrastructure.util.UserValidator.validateDocument;

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
                .orElseThrow(() -> new DomainException(DomainConstants.MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setPassword(null);
        return user;
    }

    @Override
    public Auth login(Auth auth) {
        User user = userPersistencePort.findByEmail(auth.getEmail())
                .orElseThrow(() -> new DomainException(DomainConstants.MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!passwordServicePort.matches(auth.getPassword(), user.getPassword())) {
            throw new DomainException(DomainConstants.MSG_INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }

        return setTokenProperties(auth, user);
    }

    private Auth setTokenProperties(Auth auth, User user) {
        Map<String, Object> userClaims = getClaims(user);

        auth.setToken(tokenServicePort.generateToken(user.getEmail(), userClaims));
        auth.setPassword(null);
        auth.setEmail(null);
        return auth;
    }

    private Map<String, Object> getClaims(User user) {
        return Map.of(
                DomainConstants.KEY_USER_ID, user.getId(),
                DomainConstants.KEY_ROLE_NAME, user.getRole().name()
        );
    }

    private void validateUser(User userEntity, boolean validateAge) {
        if (!validateCellphone(userEntity.getPhoneNumber())) {
            throw new DomainException(DomainConstants.MSG_INVALID_CELLPHONE, HttpStatus.BAD_REQUEST);
        }
        if (!validateDocument(userEntity.getIdentificationNumber())) {
            throw new DomainException(DomainConstants.MSG_INVALID_DOCUMENT, HttpStatus.BAD_REQUEST);
        }
        if (validateAge && Period.between(userEntity.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new DomainException(DomainConstants.MSG_UNDERAGE_USER, HttpStatus.BAD_REQUEST);
        }
        if (userPersistencePort.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new DomainException(DomainConstants.MSG_EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }
        if (userPersistencePort.findByPhoneNumber(userEntity.getPhoneNumber()).isPresent()) {
            throw new DomainException(DomainConstants.MSG_PHONE_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }
    }
}
