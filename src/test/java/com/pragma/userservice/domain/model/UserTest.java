package com.pragma.userservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void gettersAndSettersWork() {
        User user = new User();
        LocalDate birth = LocalDate.now();

        user.setId(1L);
        user.setName("John");
        user.setLastName("Doe");
        user.setIdentificationNumber("123456");
        user.setPhoneNumber("+573000000000");
        user.setBirthDate(birth);
        user.setEmail("john@example.com");
        user.setPassword("secret");
        user.setRole(Role.CLIENT);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastName());
        assertEquals("123456", user.getIdentificationNumber());
        assertEquals("+573000000000", user.getPhoneNumber());
        assertEquals(birth, user.getBirthDate());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("secret", user.getPassword());
        assertEquals(Role.CLIENT, user.getRole());
    }

    @Test
    void allArgsConstructorAssignsFields() {
        LocalDate birth = LocalDate.now();
        User user = new User(1L, "Jane", "Smith", "654321", "+573111111111", birth, "jane@example.com", "pwd", Role.OWNER);

        assertEquals(1L, user.getId());
        assertEquals("Jane", user.getName());
        assertEquals("Smith", user.getLastName());
        assertEquals("654321", user.getIdentificationNumber());
        assertEquals("+573111111111", user.getPhoneNumber());
        assertEquals(birth, user.getBirthDate());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("pwd", user.getPassword());
        assertEquals(Role.OWNER, user.getRole());
    }
}
