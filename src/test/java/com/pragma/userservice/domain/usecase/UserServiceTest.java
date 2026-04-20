package com.pragma.userservice.domain.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import com.pragma.userservice.domain.api.IPasswordServicePort;
import com.pragma.userservice.domain.api.ITokenServicePort;
import com.pragma.userservice.domain.constants.DomainConstants;
import com.pragma.userservice.domain.exception.DomainException;
import com.pragma.userservice.domain.model.Auth;
import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.domain.api.IAuthenticationServicePort;
import com.pragma.userservice.domain.spi.IRestaurantAssignmentPort;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import com.pragma.userservice.testdata.builders.AuthBuilder;
import com.pragma.userservice.testdata.builders.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordServicePort passwordServicePort;

    @Mock
    private ITokenServicePort tokenServicePort;

    @Mock
    private IAuthenticationServicePort authenticationServicePort;

    @Mock
    private IRestaurantAssignmentPort restaurantAssignmentPort;

    @InjectMocks
    private UserService userService;

    private User validUser;
    private User storedUser;
    private Auth authRequest;

    @BeforeEach
    void setUp() {
        validUser = UserBuilder.aUser()
                .withId(null)
                .withBirthDate(LocalDate.now().minusYears(20))
                .withPassword("raw")
                .withRole(null)
                .build();

        storedUser = UserBuilder.aUser()
                .withId(1L)
                .withPassword("encodedPassword")
                .withRole(Role.CLIENT)
                .build();

        authRequest = AuthBuilder.anAuth()
                .withEmail("john@example.com")
                .withPassword("raw123")
                .build();

        lenient().when(passwordServicePort.encodePassword("raw")).thenReturn("encoded");
        lenient().when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        lenient().when(userPersistencePort.findByPhoneNumber(any())).thenReturn(Optional.empty());
    }

    @Test
    void createAdminSuccess() {
        userService.createAdmin(validUser);

        assertEquals("encoded", validUser.getPassword());
        verify(userPersistencePort).saveUser(validUser, Role.ADMIN);
    }

    @Test
    void createOwnerSuccess() {
        userService.createOwner(validUser);
        assertEquals("encoded", validUser.getPassword());
        verify(userPersistencePort).saveUser(validUser, Role.OWNER);
    }

    @Test
    void createEmployeeSuccess() {
        User savedEmployee = UserBuilder.aUser().withId(99L).build();
        when(authenticationServicePort.getAuthenticatedUserId()).thenReturn(Optional.of(10L));
        when(userPersistencePort.findByEmail(validUser.getEmail()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(savedEmployee));

        userService.createEmployee(validUser);

        verify(userPersistencePort).saveUser(validUser, Role.EMPLOYEE);
        verify(restaurantAssignmentPort).assignEmployeeToOwnerRestaurant(10L, 99L);
    }

    @Test
    void createClientAllowsUnderage() {
        validUser.setBirthDate(LocalDate.now().minusYears(16));
        userService.createClient(validUser);
        verify(userPersistencePort).saveUser(validUser, Role.CLIENT);
    }

    @Test
    void createOwnerInvalidCellphone() {
        validUser.setPhoneNumber("12");

        DomainException exception = assertThrows(DomainException.class, () -> userService.createOwner(validUser));

        assertEquals(DomainConstants.MSG_INVALID_CELLPHONE, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        verify(userPersistencePort, never()).saveUser(any(), any());
    }

    @Test
    void createOwnerInvalidDocument() {
        validUser.setIdentificationNumber("abc");

        DomainException exception = assertThrows(DomainException.class, () -> userService.createOwner(validUser));

        assertEquals(DomainConstants.MSG_INVALID_DOCUMENT, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        verify(userPersistencePort, never()).saveUser(any(), any());
    }

    @Test
    void createOwnerUnderAge() {
        validUser.setBirthDate(LocalDate.now().minusYears(17));

        DomainException exception = assertThrows(DomainException.class, () -> userService.createOwner(validUser));

        assertEquals(DomainConstants.MSG_UNDERAGE_USER, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        verify(userPersistencePort, never()).saveUser(any(), any());
    }

    @Test
    void createOwnerEmailExists() {
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(Optional.of(new User()));

        DomainException exception = assertThrows(DomainException.class, () -> userService.createOwner(validUser));

        assertEquals(DomainConstants.MSG_EMAIL_ALREADY_EXISTS, exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }

    @Test
    void createOwnerPhoneExists() {
        when(userPersistencePort.findByPhoneNumber(validUser.getPhoneNumber())).thenReturn(Optional.of(new User()));

        DomainException exception = assertThrows(DomainException.class, () -> userService.createOwner(validUser));

        assertEquals(DomainConstants.MSG_PHONE_ALREADY_EXISTS, exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }

    @Test
    void getUserByIdRemovesPassword() {
        User userWithPassword = UserBuilder.aUser()
                .withId(1L)
                .withPassword("secretPassword")
                .build();

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(userWithPassword));

        User result = userService.getUserById(1L);

        assertNull(result.getPassword());
        assertEquals("John", result.getName());
    }

    @Test
    void getUserByIdNotFound() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> userService.getUserById(1L));

        assertEquals(DomainConstants.MSG_USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void loginSuccess() {
        when(userPersistencePort.findByEmail("john@example.com")).thenReturn(Optional.of(storedUser));
        when(passwordServicePort.matches("raw123", "encodedPassword")).thenReturn(true);
        when(tokenServicePort.generateToken(storedUser)).thenReturn("jwt-token-123");

        Auth result = userService.login(authRequest);

        assertEquals("jwt-token-123", result.getToken());
        assertNull(result.getPassword());
        assertNull(result.getEmail());
    }

    @Test
    void loginUserNotFound() {
        when(userPersistencePort.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        Auth auth = AuthBuilder.anAuth()
                .withEmail("unknown@example.com")
                .withPassword("password")
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> userService.login(auth));

        assertEquals(DomainConstants.MSG_USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void loginInvalidCredentials() {
        when(userPersistencePort.findByEmail("john@example.com")).thenReturn(Optional.of(storedUser));
        when(passwordServicePort.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        Auth auth = AuthBuilder.anAuth()
                .withEmail("john@example.com")
                .withPassword("wrongPassword")
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> userService.login(auth));

        assertEquals(DomainConstants.MSG_INVALID_CREDENTIALS, exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());
    }
}
