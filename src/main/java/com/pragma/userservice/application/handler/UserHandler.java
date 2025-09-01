package com.pragma.userservice.application.handler;

import com.pragma.userservice.application.dto.UserDTO;
import com.pragma.userservice.application.mapper.IUserDTOMapper;
import com.pragma.userservice.domain.api.IUserServicePort;
import com.pragma.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserDTOMapper userDTOMapper;
    private final IUserServicePort userServicePort;

    @Override
    public void createOwner(UserDTO userDTO) {
        User userEntity = userDTOMapper.toEntity(userDTO);
        userServicePort.createOwner(userEntity);
    }

    @Override
    public void createEmployee(UserDTO userDTO) {
        User userEntity = userDTOMapper.toEntity(userDTO);
        userServicePort.createEmployee(userEntity);
    }

    @Override
    public void createClient(UserDTO userDTO) {
        User userEntity = userDTOMapper.toEntity(userDTO);
        userServicePort.createClient(userEntity);
    }
}
