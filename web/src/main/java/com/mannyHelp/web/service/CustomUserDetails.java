package com.mannyHelp.web.service;


import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetails(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = (Users) usersRepository.findByUsername(username);


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),

                Collections.emptyList()
        );
    }
}