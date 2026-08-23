package com.mannyHelp.web.controllers;

import com.mannyHelp.web.dto.UsersDto;
import com.mannyHelp.web.models.ChatMessage;
import com.mannyHelp.web.models.OferitorServicii;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.ChatMessageRepository;
import com.mannyHelp.web.repository.OferitorServiciiRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.CustomUserDetails;
import com.mannyHelp.web.service.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final OferitorServiciiRepository oferitorRepository; // <-- Adăugat pentru rezolvarea providerId -> userId

    public ChatController(ChatMessageRepository chatMessageRepository,
                          UsersRepository usersRepository,
                          UsersService usersService,
                          OferitorServiciiRepository oferitorRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
        this.oferitorRepository = oferitorRepository;
    }
    @GetMapping("/{targetId}")
    public String openChat(@PathVariable("targetId") Long targetId,
                           @AuthenticationPrincipal CustomUserDetails customUser,
                           Model model) {
        if (customUser == null) {
            return "redirect:/login";
        }

        UsersDto loggedUser = usersService.findUserByUsername(customUser.getUsername());
        if (loggedUser == null) {
            return "redirect:/login";
        }


        OferitorServicii provider = oferitorRepository.findById(targetId).orElse(null);
        UsersDto partner = null;

        if (provider != null && provider.getUser() != null) {
            partner = usersService.findUserById(provider.getUser().getUserid());
        } else {

            partner = usersService.findUserById(targetId);
        }


        if (partner == null) {
            return "redirect:/mainPage";
        }

        List<ChatMessage> messages = chatMessageRepository.findConversation(loggedUser.getUserid(), partner.getUserid());

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("partner", partner);
        model.addAttribute("messages", messages);

        return "chat";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam("receiverId") Long receiverId,
                              @RequestParam("content") String content,
                              @AuthenticationPrincipal CustomUserDetails customUser) {
        if (customUser == null) {
            return "redirect:/login";
        }

        Users sender = usersRepository.findByUsername(customUser.getUsername());
        Users receiver = usersRepository.findById(receiverId).orElse(null);

        if (sender != null && receiver != null && content != null && !content.trim().isEmpty()) {
            ChatMessage message = ChatMessage.builder()
                    .senderId(sender)
                    .receiverId(receiver)
                    .content(content.trim())
                    .sentAt(LocalDateTime.now())
                    .isRead(false)
                    .build();
            chatMessageRepository.save(message);
        }

        return "redirect:/chat/" + receiverId;
    }
}