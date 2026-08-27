package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.ProgramareService;
import com.mannyHelp.web.service.RecenzieService;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class RecenzieController {

    private final RecenzieService recenzieService;
    private final UsersService usersService;
    private final ProgramareService programareService;

    public RecenzieController(RecenzieService recenzieService,
                              UsersService usersService,
                              ProgramareService programareService) {
        this.recenzieService = recenzieService;
        this.usersService = usersService;
        this.programareService = programareService;
    }

    @PostMapping("/booking/review")
    public String submitReview(@RequestParam("userId") Long userId,
                               @RequestParam("serviceId") Long serviceId,
                               @RequestParam("providerId") Long providerId,
                               @RequestParam("rating") int rating,
                               @RequestParam("comment") String comment,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());
        if (loggedUser == null) {
            return "redirect:/login";
        }

        // Trimitem toți cei 5 parametri (inclusiv serviceId convertit în Integer)
        recenzieService.addRecenzie(loggedUser.getUserid(), providerId, serviceId.intValue(), rating, comment);
        programareService.markReviewAsSubmitted(userId, serviceId, providerId);

        redirectAttributes.addFlashAttribute("reviewSuccessMessage", "Mulțumim pentru feedback!");
        return "redirect:/users/" + loggedUser.getUserid();
    }

    @PostMapping("/add-review")
    public String addReview(@RequestParam("providerId") Long providerId,
                            @RequestParam("serviceId") Integer serviceId,
                            @RequestParam("rating") int rating,
                            @RequestParam("comment") String comment,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());
        if (loggedUser == null) {
            return "redirect:/login";
        }

        // Trimitem toți cei 5 parametri (inclusiv serviceId)
        recenzieService.addRecenzie(loggedUser.getUserid(), providerId, serviceId, rating, comment);
        redirectAttributes.addFlashAttribute("reviewSuccessMessage", "Recenzia a fost adăugată cu succes!");

        return "redirect:/service/" + serviceId;
    }

    @PostMapping("/reviews/{id}/respond")
    public String respondToReview(@PathVariable("id") Integer reviewId,
                                  @RequestParam("providerResponse") String providerResponse,
                                  @RequestParam(value = "serviceId", required = false) Integer serviceId,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());
        if (loggedUser == null) {
            return "redirect:/login";
        }

        recenzieService.addProviderResponse(reviewId, providerResponse);

        redirectAttributes.addFlashAttribute("reviewSuccessMessage", "Răspunsul tău a fost adăugat!");

        if (serviceId != null) {
            return "redirect:/service/" + serviceId;
        }
        return "redirect:/users/" + loggedUser.getUserid();
    }
}