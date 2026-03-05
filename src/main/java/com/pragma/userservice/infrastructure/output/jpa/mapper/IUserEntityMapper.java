package com.pragma.userservice.infrastructure.output.jpa.mapper;

import com.pragma.userservice.domain.model.Role;
import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.userservice.infrastructure.output.jpa.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserEntityMapper {

    @Mapping(target = "roleEntity", ignore = true)
    UserEntity toEntity(User user);

    @Mapping(target = "role", expression = "java(mapRoleEntityToRole(userEntity.getRoleEntity()))")
    User toDomain(@NotNull UserEntity userEntity);

    default Role mapRoleEntityToRole(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }
        return roleEntity.getName();
    }
}