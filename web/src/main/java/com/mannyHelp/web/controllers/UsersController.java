package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.ProgramareDto;
import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.Recenzie;
import com.mannyHelp.web.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UsersService usersService;
    private final ProgramareService programareService;
    private final RecenzieService recenzieService;
    private final ServiceService serviceService;

    public UsersController(UsersService usersService,
                           ProgramareService programareService,
                           RecenzieService recenzieService, ServiceService serviceService) {
        this.usersService = usersService;
        this.programareService = programareService;
        this.recenzieService = recenzieService;
        this.serviceService = serviceService;
    }

    @GetMapping("/mainPage")
    public String getMainPage(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) String location,
                              @RequestParam(required = false) String category,
                              @RequestParam(required = false) String sortBy,
                              Model model, Principal principal) {
        List<UsersDto> users = usersService.findAllUsers();

        List<ServiceDto> services = serviceService.searchAndFilterServices(keyword, location, category, sortBy);
        List<Recenzie> latestReviews = recenzieService.getRecentPlatformReviews(6);
        model.addAttribute("users", users);

        UsersDto loggedUser = null;
        if (principal != null) {
            loggedUser = usersService.findUserByUsername(principal.getName());
        }
        model.addAttribute("services", services);
        model.addAttribute("latestReviews", latestReviews);
        model.addAttribute("users", users);
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
    public String userDetails(@PathVariable("id") Long userId,
                              @AuthenticationPrincipal CustomUserDetails customUser,
                              @RequestParam(value = "reviewLimit", defaultValue = "5") Integer reviewLimit,
                              Model model) {

        UsersDto user = usersService.findUserById(userId);

        UsersDto loggedUser = null;
        if (customUser != null) {
            loggedUser = usersService.findUserByUsername(customUser.getUsername());
        }

        List<ProgramareDto> programari;
        List<Recenzie> userReviews = null;
        List<Recenzie> providerReceivedReviews = null;
        double providerAverageRating = 0.0;

        if (Boolean.TRUE.equals(user.getUserOrProvider())) {
            // DACĂ ESTE PROVIDER: Preluăm programările primite, recenziile clienților și media
            programari = programareService.getProgramariByProviderUserId(userId);
            providerReceivedReviews = recenzieService.getRecenziiByProvider(userId);
            providerAverageRating = recenzieService.getAverageRatingByProvider(userId);
        } else {
            // DACĂ ESTE CLIENT: Preluăm programările făcute și recenziile scrise de el
            programari = programareService.getProgramariByUserId(userId);
            if (loggedUser != null && loggedUser.getUserid().equals(userId)) {
                userReviews = recenzieService.getRecenziiByUserId(userId, reviewLimit);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("programari", programari);
        model.addAttribute("userReviews", userReviews);
        model.addAttribute("providerReviews", providerReceivedReviews);
        model.addAttribute("averageRating", providerAverageRating);
        model.addAttribute("selectedLimit", reviewLimit);

        return "user-details";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());

        if (!loggedUser.getUserid().equals(id)) {
            return "redirect:/users/" + loggedUser.getUserid();
        }
        model.addAttribute("user", loggedUser);

        return "edit-user";
    }

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

    @PostMapping("/programari/status")
    public String updateProgramareStatus(@RequestParam("userId") Long userId,
                                         @RequestParam("serviceId") Long serviceId,
                                         @RequestParam("providerId") Long providerId,
                                         @RequestParam("status") String status,
                                         @AuthenticationPrincipal CustomUserDetails customUser) {
        if (customUser == null) {
            return "redirect:/login";
        }

        programareService.updateStatus(userId, serviceId, providerId, status);

        UsersDto loggedUser = usersService.findUserByUsername(customUser.getUsername());
        return "redirect:/users/" + loggedUser.getUserid();
    }
}