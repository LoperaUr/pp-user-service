package com.pragma.userservice.domain.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import com.pragma.userservice.domain.api.IPasswordServicePort;
import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordServicePort passwordServicePort;

    @InjectMocks
    private UserService userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User(null, "John", "Doe", "123456", "+573000000000",
                LocalDate.now().minusYears(20), "john@example.com", "raw", null);
        when(passwordServicePort.encodePassword("raw")).thenReturn("encoded");
        when(userPersistencePort.findByEmail(any())).thenReturn(Optional.empty());
        when(userPersistencePort.findByPhoneNumber(any())).thenReturn(Optional.empty());
    }

    @Test
    void createOwnerSuccess() {
        userService.createOwner(validUser);
        assertEquals("encoded", validUser.getPassword());
        verify(userPersistencePort).saveUser(validUser, Role.OWNER);
    }

    @Test
    void createEmployeeSuccess() {
        userService.createEmployee(validUser);
        verify(userPersistencePort).saveUser(validUser, Role.EMPLOYEE);
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
        assertThrows(IllegalArgumentException.class, () -> userService.createOwner(validUser));
        verify(userPersistencePort, never()).saveUser(any(), any());
    }

    @Test
    void createOwnerInvalidDocument() {
        validUser.setIdentificationNumber("abc");
        assertThrows(IllegalArgumentException.class, () -> userService.createOwner(validUser));
        verify(userPersistencePort, never()).saveUser(any(), any());
    }

    @Test
    void createOwnerUnderAge() {
        validUser.setBirthDate(LocalDate.now().minusYears(17));
        assertThrows(IllegalArgumentException.class, () -> userService.createOwner(validUser));
        verify(userPersistencePort, never()).saveUser(any(), any());
    }

    @Test
    void createOwnerEmailExists() {
        when(userPersistencePort.findByEmail(validUser.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(IllegalArgumentException.class, () -> userService.createOwner(validUser));
    }

    @Test
    void createOwnerPhoneExists() {
        when(userPersistencePort.findByPhoneNumber(validUser.getPhoneNumber())).thenReturn(Optional.of(new User()));
        assertThrows(IllegalArgumentException.class, () -> userService.createOwner(validUser));
    }

    @Test
    void getUserByIdRemovesPassword() {
        User userWithPassword = User.builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .password("secretPassword")
                .build();
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(userWithPassword));
        
        User result = userService.getUserById(1L);

        assertNull(result.getPassword());
        assertEquals("John", result.getName());
    }

    @Test
    void getUserByIdNotFound() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
    }
}
