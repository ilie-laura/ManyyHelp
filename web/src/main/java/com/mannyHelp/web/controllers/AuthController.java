package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsersService usersService;

    public AuthController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new UsersDto());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UsersDto userDto) {

        System.out.println("Salvez userul: " + userDto.getUsername());

        usersService.saveUser(userDto);


        return "redirect:/login?success";
    }
}