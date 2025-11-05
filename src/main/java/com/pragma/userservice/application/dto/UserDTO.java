package com.pragma.userservice.application.dto;

import com.pragma.userservice.application.constants.ApplicationConstants;
import com.pragma.userservice.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = ApplicationConstants.NAME_CANNOT_BE_BLANK)
    private String name;

    @NotBlank(message = ApplicationConstants.LAST_NAME_CANNOT_BE_BLANK)
    private String lastName;

    @NotBlank(message = ApplicationConstants.IDENTIFICATION_NUMBER_CANNOT_BE_BLANK)
    private String identificationNumber;

    @NotBlank(message = ApplicationConstants.PHONE_NUMBER_CANNOT_BE_BLANK)
    private String phoneNumber;

    @NotNull(message = ApplicationConstants.BIRTH_DATE_CANNOT_BE_NULL)
    private LocalDate birthDate;

    @Email(message = ApplicationConstants.EMAIL_SHOULD_BE_VALID)
    @NotBlank(message = ApplicationConstants.EMAIL_CANNOT_BE_BLANK)
    private String email;

    @NotBlank(message = ApplicationConstants.PASSWORD_CANNOT_BE_BLANK)
    private String password;

    private Role role;

}
