package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.Recenzie;
import com.mannyHelp.web.models.Service;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.service.OferitorServiciiService;
import com.mannyHelp.web.service.RecenzieService;
import com.mannyHelp.web.service.ServiceService;
import com.mannyHelp.web.service.UsersService;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ServiceController {

    private final ServiceService servicesService;
    private final UsersService usersService;
    private final OferitorServiciiService oferitorServiciiService;
    private final RecenzieService recenzieService;

    public ServiceController(ServiceService servicesService, UsersService usersService, OferitorServiciiService oferitorServiciiService, RecenzieService recenzieService) {
        this.servicesService = servicesService;
        this.usersService = usersService;
        this.oferitorServiciiService = oferitorServiciiService;
        this.recenzieService = recenzieService;
    }
    @GetMapping("/browse-services")
    public String browseServices(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String location,
                                 HttpSession session,
                                 Model model) {





        List<ServiceDto> services = servicesService.searchServices(keyword, location);
        model.addAttribute("services", services);

        return "mainPage"; 
    }
    @GetMapping("/service/{id}")
    public String getServiceDetails(@PathVariable("id") int serviceId,
                                    Model model,
                                    java.security.Principal principal) { // 1. Adaugă Principal

        ServiceDto serviceDto = servicesService.findServiceById(serviceId);
        OferitorServiciiDto oferitorDto = oferitorServiciiService.findByServiceId(serviceId);

        // 2. Preluăm utilizatorul logat
        UsersDto loggedUser = null;
        if (principal != null) {
            loggedUser = usersService.findUserByUsername(principal.getName());
        }

        List<Recenzie> reviews = null;
        double averageRating = 0.0;

        Long targetProviderId = null;
        if (oferitorDto != null && oferitorDto.getProviderid() != null) {
            targetProviderId = oferitorDto.getProviderid();
        } else if (serviceDto != null) {
            targetProviderId = serviceDto.getProviderId();
        }

        if (targetProviderId != null) {
            reviews = recenzieService.getRecenziiByProvider(targetProviderId);
            averageRating = recenzieService.getAverageRatingByProvider(targetProviderId);
        }

        model.addAttribute("service", serviceDto);
        model.addAttribute("oferitor", oferitorDto);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("loggedUser", loggedUser); // 3. Adaugă loggedUser în Model!

        return "service-details";
    }
    @GetMapping("/services/new")
    public String showAddServiceForm(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());


        if (!loggedUser.isUserOrProvider()) {
            return "redirect:/browse-services";
        }


        model.addAttribute("service", new ServiceDto());

        return "add-service";
    }


    @PostMapping("/services/new")
    public String saveService(@ModelAttribute("service") ServiceDto serviceDto, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());

        if (!loggedUser.isUserOrProvider()) {
            return "redirect:/browse-services";
        }

        serviceDto.setProviderId(loggedUser.getUserid());
        servicesService.saveService(serviceDto);

        return "redirect:/browse-services?success";
    }
}



