package com.mannyHelp.web.controllers;

import com.mannyHelp.web.service.CustomUserDetails;
import com.mannyHelp.web.service.ProgramareService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class ProgramareController {

    // 1. Declarăm serviciul ca câmp final în controller
    private final ProgramareService programareService;

    // 2. Îl injectăm prin constructor
    public ProgramareController(ProgramareService programareService) {
        this.programareService = programareService;
    }

    @PostMapping("/book-service")
    public String bookService(
            @RequestParam("serviceId") int serviceId,
            @RequestParam("providerId") int providerId,
            @RequestParam("bookingDate") String bookingDate,
            @RequestParam("bookingTime") String bookingTime,
            @AuthenticationPrincipal CustomUserDetails loggedUser,
            RedirectAttributes redirectAttributes) {

        if (loggedUser == null) {
            return "redirect:/login";
        }

        LocalDateTime dataProgramare = LocalDateTime.parse(bookingDate + "T" + bookingTime);


        Long userId = loggedUser.getUserid();


        programareService.createProgramare(userId, serviceId, providerId, dataProgramare);

        redirectAttributes.addFlashAttribute("successMessage", "Programarea a fost înregistrată cu succes!");

        return "redirect:/service/" + serviceId;
    }
}