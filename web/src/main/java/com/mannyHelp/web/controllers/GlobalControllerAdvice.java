package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.ChatMessageRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.UsersService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final ChatMessageRepository chatMessageRepository;
    private final UsersService usersService;
    private final UsersRepository usersRepository;

    public GlobalControllerAdvice(ChatMessageRepository chatMessageRepository, UsersService usersService, UsersRepository usersRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }
    @ModelAttribute("unreadChatCount")
    public long getUnreadChatCount(@AuthenticationPrincipal UserDetails customUser) {
        if (customUser == null) return 0;
        Users user = usersRepository.findByUsername(customUser.getUsername());
        return user != null ? chatMessageRepository.countUnreadMessagesByUserId(user.getUserid()) : 0;
    }

    @ModelAttribute("lastChatPartnerId")
    public Long getLastChatPartnerId(@AuthenticationPrincipal UserDetails customUser) {
        if (customUser == null) return null;
        Users user = usersRepository.findByUsername(customUser.getUsername());
        if (user == null) return null;
        Long lastPartner = chatMessageRepository.findLastChatPartnerUserId(user.getUserid());
        return lastPartner != null ? lastPartner : user.getUserid();
    }

    @ModelAttribute("loggedUser")
    public UsersDto addLoggedUserToModel() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return usersService.findUserByUsername(auth.getName());
        }
        return null;
    }
}