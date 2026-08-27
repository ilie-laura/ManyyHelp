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
    @GetMapping({ "/browser-services"})
    public String browseServices(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String location,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false) String sortBy,
                                 Principal principal,
                                 Model model) {

        List<UsersDto> users = usersService.findAllUsers();
        List<Recenzie> latestReviews = recenzieService.getRecentPlatformReviews(3);


        List<ServiceDto> services = servicesService.searchAndFilterServices(keyword, location, category, sortBy);

        UsersDto loggedUser = null;
        if (principal != null) {
            loggedUser = usersService.findUserByUsername(principal.getName());
        }

        model.addAttribute("services", services);
        model.addAttribute("users", users);
        model.addAttribute("latestReviews", latestReviews);
        model.addAttribute("loggedUser", loggedUser);

        return "browser-services";
    }@GetMapping("/service/{id}")
    public String getServiceDetails(@PathVariable("id") int serviceId,
                                    Model model,
                                    Principal principal) {

        ServiceDto serviceDto = servicesService.findServiceById(serviceId);
        OferitorServiciiDto oferitorDto = oferitorServiciiService.findByServiceId(serviceId);

        UsersDto loggedUser = null;
        if (principal != null) {
            loggedUser = usersService.findUserByUsername(principal.getName());
        }

      
        List<Recenzie> reviews = recenzieService.getRecenziiByService(serviceId);
        double averageRating = recenzieService.getAverageRatingByService(serviceId);

        model.addAttribute("service", serviceDto);
        model.addAttribute("oferitor", oferitorDto);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("loggedUser", loggedUser);

        return "service-details";
    }
    @GetMapping("/services/new")
    public String showAddServiceForm(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(principal.getName());


        if (!loggedUser.isUserOrProvider()) {
            return "redirect:/browser-services";
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
            return "redirect:/browser-services";
        }

        serviceDto.setProviderId(loggedUser.getUserid());
        servicesService.saveService(serviceDto);

        return "redirect:/browser-services?success";
    }
    @GetMapping("/providers/{id}/services")
    public String viewProviderServices(@PathVariable("id") Long providerUserId, Model model) {
        UsersDto providerUser = usersService.findUserById(providerUserId);
        List<ServiceDto> providerServices = servicesService.findServicesByProviderUserId(providerUserId);
        double averageRating = recenzieService.getAverageRatingByProvider(providerUserId);

        model.addAttribute("provider", providerUser);
        model.addAttribute("services", providerServices);
        model.addAttribute("averageRating", averageRating);

        return "provider-services";
    }
}



