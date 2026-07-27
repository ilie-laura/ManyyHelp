package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.Users;

import java.util.List;

public interface UsersService {
    List<UsersDto> findAllUsers();

    void saveUser(UsersDto userDto);

    UsersDto findUserByUsername(String username);

    void updateUser(String username, UsersDto userDto);

    void deleteUser(String username);

    UsersDto findUserById(Long id);
}