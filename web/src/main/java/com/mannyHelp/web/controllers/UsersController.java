package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.Users;
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
}