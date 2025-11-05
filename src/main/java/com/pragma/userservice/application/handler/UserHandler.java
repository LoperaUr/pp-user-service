package com.pragma.userservice.application.handler;

import com.pragma.userservice.application.dto.AuthDTO;
import com.pragma.userservice.application.dto.UserDTO;
import com.pragma.userservice.application.mapper.IAuthMapper;
import com.pragma.userservice.application.mapper.IUserMapper;
import com.pragma.userservice.domain.api.IUserServicePort;
import com.pragma.userservice.domain.model.Auth;
import com.pragma.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserMapper userDTOMapper;
    private final IAuthMapper authMapper;
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

    @Override
    public UserDTO getUserById(Long id) {
        User user = userServicePort.getUserById(id);
        return userDTOMapper.toDto(user);
    }

    @Override
    public AuthDTO login(AuthDTO authDTO) {
        Auth authEntity = authMapper.toEntity(authDTO);
        Auth result = userServicePort.login(authEntity);
        return authMapper.toDto(result);
    }
}
