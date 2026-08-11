package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.service.OferitorServiciiService;
import com.mannyHelp.web.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsersService usersService;
    private final OferitorServiciiService oferitorServiciiService;

    public AuthController(UsersService usersService, OferitorServiciiService oferitorServiciiService) {
        this.usersService = usersService;
        this.oferitorServiciiService = oferitorServiciiService;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new UsersDto());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UsersDto userDto) {

        // Verificăm dacă utilizatorul a bifat Prestator (Provider)
        if (Boolean.TRUE.equals(userDto.getUserOrProvider())) {

            OferitorServiciiDto oferitorDto = OferitorServiciiDto.builder()
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .nume(userDto.getNume())
                    .prenume(userDto.getPrenume())
                    .photoUrl(userDto.getPhotourl())
                    .numeCompanie(userDto.getNumeCompanie() != null && !userDto.getNumeCompanie().isBlank()
                            ? userDto.getNumeCompanie()
                            : userDto.getNume() + " " + userDto.getPrenume() + " SRL")
                    .cui(userDto.getCui())
                    .telefonContact(userDto.getTelefonContact())
                    .descriereServicii(userDto.getDescriereServicii())
                    .build();

            oferitorServiciiService.saveOferitorServicii(oferitorDto);
        } else {

            usersService.saveUser(userDto);
        }

        return "redirect:/login?success";
    }
}