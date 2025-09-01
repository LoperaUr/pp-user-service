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

        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastName());
        assertEquals("123456", user.getIdentificationNumber());
        assertEquals("+573000000000", user.getPhoneNumber());
        assertEquals(birth, user.getBirthDate());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("secret", user.getPassword());
    }

    @Test
    void allArgsConstructorAssignsFields() {
        LocalDate birth = LocalDate.now();
        User user = new User(1L, "Jane", "Smith", "654321", "+573111111111", birth, "jane@example.com", "pwd");

        assertEquals(1L, user.getId());
        assertEquals("Jane", user.getName());
        assertEquals("Smith", user.getLastName());
        assertEquals("654321", user.getIdentificationNumber());
        assertEquals("+573111111111", user.getPhoneNumber());
        assertEquals(birth, user.getBirthDate());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("pwd", user.getPassword());
    }
}
