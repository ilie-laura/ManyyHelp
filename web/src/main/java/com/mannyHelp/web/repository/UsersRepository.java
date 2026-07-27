package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users,Long> {
   Users findByUsername(String username);

    Users findByUserid(long userid);
}
