package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.UsersService;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UsersService usersService;

    public GlobalControllerAdvice(UsersService usersService) {
        this.usersService = usersService;
    }

    @ModelAttribute("loggedUser")
    public UsersDto addLoggedUserToModel(Principal principal) {
        if (principal != null) {
            return usersService.findUserByUsername(principal.getName());
        }
        return null;
    }
}