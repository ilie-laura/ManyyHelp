package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.service.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ServiceController {

    private final ServiceService servicesService;

    public ServiceController(ServiceService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping("/browse-services")
    public String browseServices(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "location", required = false) String location, // <-- Aici adaugi parametrul
            Model model) {

        // Acum ambii parametri sunt recunoscuți și trimiși către Service
        List<ServiceDto> services = servicesService.searchServices(keyword, location);

        model.addAttribute("services", services);

        return "mainPage";
    }
    @GetMapping("/service/{id}")
    public String getServiceDetails(@PathVariable("id") int id, Model model) {
        ServiceDto service = servicesService.findServiceById(id);

        if (service == null) {
            return "redirect:/browse-services";
        }


        model.addAttribute("service", service);
        // model.addAttribute("oferitor", oferitorServiciiService.findByServiceId(id));
       //  model.addAttribute("reviews", recenzieService.findByServiceId(id));

        return "service-details";
    }

}
