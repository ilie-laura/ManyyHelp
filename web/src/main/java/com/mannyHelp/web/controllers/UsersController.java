package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/mainPage")
    public String getMainPage(Model model, Principal principal) {
        List<UsersDto> users = usersService.findAllUsers();
        model.addAttribute("users", users);

        UsersDto loggedUser = null;
        if (principal != null) {
            loggedUser = usersService.findUserByUsername(principal.getName());
        }
        model.addAttribute("loggedUser", loggedUser);

        return "mainPage";
    }

    @GetMapping("/users-list")
    public String getAllUsers(Model model) {
        List<UsersDto> usersList = usersService.findAllUsers();
        model.addAttribute("users", usersList);
        return "users-list";
    }

    @GetMapping("/users/{id}")
    public String getUserDetails(@PathVariable("id") Long id, Model model, Principal principal) {
        UsersDto user = usersService.findUserById(id);
        model.addAttribute("user", user);

        if (principal != null) {
            UsersDto loggedUser = usersService.findUserByUsername(principal.getName());
            model.addAttribute("loggedUser", loggedUser);
        }

        return "user-details";
    }

    // --- FORMULAR EDITARE (După ID-ul utilizatorului) ---
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());

        // Verificăm dacă utilizatorul încearcă să editeze propriul profil
        if (!loggedUser.getUserid().equals(id)) {
            return "redirect:/users/" + loggedUser.getUserid();
        }

        // Trimitem obiectul "user" în model pentru formularul HTML
        model.addAttribute("user", loggedUser);

        return "edit-user";
    }

    // --- SALVARE EDITARE ---
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") UsersDto userDto,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String loggedUsername = principal.getName();
        usersService.updateUser(loggedUsername, userDto);

        UsersDto updatedUser = usersService.findUserByUsername(loggedUsername);
        return "redirect:/users/" + updatedUser.getUserid();
    }

    @PostMapping("/users/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        usersService.deleteUser(username);
        return "redirect:/users-list?deleted";
    }
}