package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }
    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        // 1. Preluăm lista de utilizatori
        List<UsersDto> usersList = usersService.findAllUsers();

        // 2. O adăugăm în Model
        model.addAttribute("users", usersList);

        // 3. Returnăm fișierul HTML (mainPage.html din templates)
        return "mainPage";
    }
    @GetMapping
    public String getAllUsers(Model model) {
        List<UsersDto> usersList = usersService.findAllUsers();

        System.out.println("Număr utilizatori trimiși în Thymeleaf: " + usersList.size());

        model.addAttribute("users", usersList);

        return "users-list"; // Numele fișierului users.html din folderul templates
    }

}