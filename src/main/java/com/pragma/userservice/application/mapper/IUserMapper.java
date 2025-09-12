package com.pragma.userservice.application.mapper;

import com.pragma.userservice.application.dto.UserDTO;
import com.pragma.userservice.domain.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserMapper {
    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);
}