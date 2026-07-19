package com.mannyHelp.web.service;

import com.mannyHelp.web.dto.UsersDto;
import java.util.List;

public interface UsersService {
    List<UsersDto> findAllUsers();
}