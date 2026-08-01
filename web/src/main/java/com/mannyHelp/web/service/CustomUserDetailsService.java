package com.mannyHelp.web.service;

import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User-ul nu a fost găsit: " + username);
        }

        // Returnăm obiectul nostru personalizat care conține și ID-ul
        return new CustomUserDetails(user);
    }
}