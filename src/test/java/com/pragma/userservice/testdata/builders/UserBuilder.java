package com.pragma.userservice.testdata.builders;

import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;

import java.time.LocalDate;

public final class UserBuilder {

    private Long id = 1L;
    private String name = "John";
    private String lastName = "Doe";
    private String identificationNumber = "123456";
    private String phoneNumber = "+573000000000";
    private LocalDate birthDate = LocalDate.now().minusYears(20);
    private String email = "john@example.com";
    private String password = "raw";
    private Role role = Role.CLIENT;

    private UserBuilder() {
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    public UserBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public User build() {
        return User.builder()
                .id(id)
                .name(name)
                .lastName(lastName)
                .identificationNumber(identificationNumber)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}

