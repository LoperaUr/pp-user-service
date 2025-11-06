package com.pragma.userservice.infrastructure.output.jpa.mapper;

import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.infrastructure.output.jpa.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserEntityMapper {

    UserEntity toEntity(User user);

    User toDomain(@NotNull UserEntity userEntity);
}