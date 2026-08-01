package com.mannyHelp.web.controllers;
import com.mannyHelp.web.dto.ServiceDto;
import com.mannyHelp.web.service.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            Model model) {

        List<ServiceDto> services = servicesService.searchServices(keyword);
        model.addAttribute("services", services);

        return "mainPage"; // Numele fișierului HTML (Thymeleaf)
    }
}