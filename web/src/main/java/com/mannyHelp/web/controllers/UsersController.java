package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/users-list")
    public String getAllUsers(Model model) {
        List<UsersDto> usersList = usersService.findAllUsers();

        System.out.println("Număr utilizatori trimiși în Thymeleaf: " + usersList.size());

        model.addAttribute("users", usersList);

        return "users-list";
    }
    @GetMapping("/users/edit/{username}")
    public String showEditForm(@PathVariable("username") String username, Model model) {
        UsersDto userDto = usersService.findUserByUsername(username);
        model.addAttribute("user", userDto);
        model.addAttribute("currentUsername", username);
        return "edit-user";
    }

    @PostMapping("/users/edit/{username}")
    public String updateUser(@PathVariable("username") String username, @ModelAttribute("user") UsersDto userDto) {
        usersService.updateUser(username, userDto);
        return "redirect:/users-list?updated";
    }

    @PostMapping("/users/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        usersService.deleteUser(username);
        return "redirect:/users-list?deleted";
    }
}