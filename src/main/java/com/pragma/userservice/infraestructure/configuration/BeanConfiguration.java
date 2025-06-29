package com.pragma.userservice.infraestructure.configuration;

import com.pragma.userservice.domain.api.IUserServicePort;import com.pragma.userservice.domain.spi.IUserPersistencePort;
import com.pragma.userservice.domain.usecase.UserService;
import com.pragma.userservice.infraestructure.output.security.adapter.PasswordServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserPersistencePort userPersistencePort;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordServiceAdapter passwordServiceAdapter(PasswordEncoder passwordEncoder) {
        return new PasswordServiceAdapter(passwordEncoder);
    }

    @Bean
    public IUserServicePort userServicePort(PasswordServiceAdapter passwordServiceAdapter) {
        return new UserService(userPersistencePort, passwordServiceAdapter);
    }
}
