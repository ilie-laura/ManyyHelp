package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.UsersDto;

import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsersDto> findAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }



    public void saveUser(UsersDto dto) {
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNume(dto.getNume());
        user.setPrenume(dto.getPrenume());
        user.setPhotourl(dto.getPhotourl());
        user.setUserOrProvider(dto.isUserOrProvider());

        usersRepository.save(user);
    }


    private UsersDto mapToDto(Users user) {
        return UsersDto.builder()
                .nume(user.getNume())
                .prenume(user.getPrenume())
                .username(user.getUsername())
                .photourl(user.getPhotoUrl())
                .userOrProvider(user.isUserOrProvider())
                .build();
    }

    public UsersDto findUserByUsername(String username) {
        Users user = usersRepository.findByUsername(username);
        return mapToDto(user);

    }
    // 2. Actualizare după Username
    public void updateUser(String username, UsersDto dto) {
        Users user = usersRepository.findByUsername(username)
                ;

        user.setNume(dto.getNume());
        user.setPrenume(dto.getPrenume());
        user.setPhotourl(dto.getPhotourl());
        user.setUserOrProvider(dto.isUserOrProvider());

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        usersRepository.save(user);
    }

    // 3. Ștergere după Username
    public void deleteUser(String username) {
        // Dacă metoda nu există în UsersRepository, o poți adăuga: void deleteByUsername(String username);
        Users user = usersRepository.findByUsername(username)
                ;
        usersRepository.delete(user);
    }
}
