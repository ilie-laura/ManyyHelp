package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.service.OferitorServiciiService;
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

    public ServiceController(ServiceService servicesService, UsersService usersService, OferitorServiciiService oferitorServiciiService) {
        this.servicesService = servicesService;
        this.usersService = usersService;
        this.oferitorServiciiService = oferitorServiciiService;
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
    public String getServiceDetails(@PathVariable("id") int serviceId, Model model) {

        // Preluăm serviciul (poate fi ServiceDto sau Service entity)
        ServiceDto serviceDto = servicesService.findServiceById(serviceId);

        // Preluăm furnizorul

        OferitorServiciiDto oferitorDto = oferitorServiciiService.findByServiceId(serviceId);

        // ⚠️ ATENȚIE LA NUMELE ATRIBUTELOR DIN MODEL:
        model.addAttribute("service", serviceDto);
        model.addAttribute("oferitor", oferitorDto); // Dacă acest nume diferă de cel din HTML, va da eroare!

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



