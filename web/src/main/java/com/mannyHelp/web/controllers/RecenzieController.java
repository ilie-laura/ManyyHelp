package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.CustomUserDetails;
import com.mannyHelp.web.service.RecenzieService;
import com.mannyHelp.web.service.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecenzieController {

    private final RecenzieService recenzieService;
    private final UsersService usersService;

    public RecenzieController(RecenzieService recenzieService, UsersService usersService) {
        this.recenzieService = recenzieService;
        this.usersService = usersService;
    }

    @PostMapping("/add-review")
    public String addReview(@RequestParam("providerId") Long providerId,
                            @RequestParam("serviceId") Integer serviceId,
                            @RequestParam("rating") int rating,
                            @RequestParam("comment") String comment,
                            @AuthenticationPrincipal CustomUserDetails customUser,
                            RedirectAttributes redirectAttributes) {

        if (customUser == null) {
            return "redirect:/login";
        }


        UsersDto loggedUser = usersService.findUserByUsername(customUser.getUsername());
        recenzieService.addRecenzie(loggedUser.getUserid(), providerId, rating, comment);
        redirectAttributes.addFlashAttribute("reviewSuccessMessage", "Recenzia a fost adăugată cu succes!");


        return "redirect:/service/" + serviceId;
    }
    @PostMapping("/reviews/{id}/respond")
    public String respondToReview(@PathVariable("id") Integer reviewId,
                                  @RequestParam("providerResponse") String providerResponse,
                                  @RequestParam("serviceId") Integer serviceId,
                                  @AuthenticationPrincipal CustomUserDetails customUser,
                                  RedirectAttributes redirectAttributes) {

        if (customUser == null) {
            return "redirect:/login";
        }

        recenzieService.addProviderResponse(reviewId, providerResponse);

        redirectAttributes.addFlashAttribute("reviewSuccessMessage", "Răspunsul tău a fost adăugat!");
        return "redirect:/service/" + serviceId;
    }
}