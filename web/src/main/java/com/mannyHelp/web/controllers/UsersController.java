package com.mannyHelp.web.controller;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/users-list")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UsersDto> usersList = usersService.findAllUsers();

        System.out.println("Număr utilizatori trimiși în Thymeleaf: " + usersList.size());

        model.addAttribute("users", usersList);

        return "users-list"; // Numele fișierului users.html din folderul templates
    }
}