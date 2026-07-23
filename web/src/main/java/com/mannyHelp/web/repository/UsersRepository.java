package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UsersRepository extends JpaRepository<Users,Long> {
   Users findByUsername(String username);
}
