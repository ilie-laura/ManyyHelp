package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.UsersRepository; // Presupunând că ai un UserRepository
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;

    // Injection prin constructor (best practice în Spring)
    public UsersServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersDto> findAllUsers() {
        List<Users> users = userRepository.findAll();

        return users.stream().map(user -> UsersDto.builder()
                .username(user.getUsername())
                .photourl(user.getPhotoUrl())
                .nume(user.getNume())
                .prenume(user.getPrenume())
                .build()
        ).collect(Collectors.toList());
    }
}