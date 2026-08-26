package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.ProgramareService;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ProgramareController {

    private final ProgramareService programareService;
    private final UsersService usersService;

    public ProgramareController(ProgramareService programareService, UsersService usersService) {
        this.programareService = programareService;
        this.usersService = usersService;
    }

    @PostMapping("/book-service")
    public String bookService(
            @RequestParam("serviceId") int serviceId,
            @RequestParam("providerId") Long providerId,
            @RequestParam("bookingDate") String bookingDate,
            @RequestParam("bookingTime") String bookingTime,
            @RequestParam(value = "detaliiSpecifice", required = false) String detaliiSpecifice,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());
        if (loggedUser == null) {
            return "redirect:/login";
        }

        LocalDateTime dataProgramare = LocalDateTime.parse(bookingDate + "T" + bookingTime);

        // Salvează programarea cu detalii
        programareService.createProgramare(loggedUser.getUserid(), serviceId, providerId, dataProgramare, detaliiSpecifice);

        redirectAttributes.addFlashAttribute("successMessage", "Programarea a fost înregistrată cu succes!");

        return "redirect:/service/" + serviceId;
    }
}