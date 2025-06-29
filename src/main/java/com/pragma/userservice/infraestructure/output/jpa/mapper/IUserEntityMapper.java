package com.pragma.userservice.infraestructure.output.jpa.mapper;

import com.pragma.userservice.domain.model.User;
import com.pragma.userservice.infraestructure.output.jpa.entity.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserEntityMapper {
    User toModel(UserEntity userEntity);

    UserEntity toEntity(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserEntity userEntity, @MappingTarget User user);
}