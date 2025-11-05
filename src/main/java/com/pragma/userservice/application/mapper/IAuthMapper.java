package com.pragma.userservice.application.mapper;

import com.pragma.userservice.application.dto.AuthDTO;
import com.pragma.userservice.domain.model.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IAuthMapper {

    Auth toEntity(AuthDTO authDTO);

    AuthDTO toDto(Auth auth);
}
