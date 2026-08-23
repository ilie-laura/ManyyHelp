package com.mannyHelp.web.controllers;

import com.mannyHelp.web.models.ChatMessage;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.ChatMessageRepository;
import com.mannyHelp.web.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatMessageRepository chatMessageRepository;
    private final UsersRepository usersRepository;

    public ChatRestController(ChatMessageRepository chatMessageRepository, UsersRepository usersRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.usersRepository = usersRepository;
    }


    @GetMapping("/{partnerId}/messages")
    public ResponseEntity<List<Map<String, Object>>> getMessages(@PathVariable Long partnerId,
                                                                 @AuthenticationPrincipal UserDetails customUser) {
        if (customUser == null) return ResponseEntity.status(401).build();

        Users loggedUser = usersRepository.findByUsername(customUser.getUsername());
        if (loggedUser == null) return ResponseEntity.status(401).build();

        chatMessageRepository.markMessagesAsRead(loggedUser.getUserid(), partnerId);
        List<ChatMessage> messages = chatMessageRepository.findConversation(loggedUser.getUserid(), partnerId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (ChatMessage m : messages) {
            Map<String, Object> map = new HashMap<>();
            map.put("content", m.getContent());
            map.put("sentByMe", m.getSenderId().getUserid().equals(loggedUser.getUserid()));
            map.put("time", m.getSentAt() != null ? m.getSentAt().toLocalTime().toString().substring(0, 5) : "");
            response.add(map);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestParam Long receiverId,
                                                           @RequestParam String content,
                                                           @AuthenticationPrincipal UserDetails customUser) {
        if (customUser == null) return ResponseEntity.status(401).build();

        Users sender = usersRepository.findByUsername(customUser.getUsername());
        Users receiver = usersRepository.findById(receiverId).orElse(null);

        if (sender != null && receiver != null && content != null && !content.trim().isEmpty()) {
            ChatMessage message = ChatMessage.builder()
                    .senderId(sender)
                    .receiverId(receiver)
                    .content(content.trim())
                    .sentAt(LocalDateTime.now())
                    .build();
            chatMessageRepository.save(message);

            Map<String, Object> res = new HashMap<>();
            res.put("success", true);
            res.put("time", message.getSentAt().toLocalTime().toString().substring(0, 5));
            return ResponseEntity.ok(res);
        }

        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/conversations")
    public ResponseEntity<List<Map<String, Object>>> getConversations(@AuthenticationPrincipal UserDetails customUser) {
        if (customUser == null) return ResponseEntity.status(401).build();

        Users loggedUser = usersRepository.findByUsername(customUser.getUsername());
        if (loggedUser == null) return ResponseEntity.status(401).build();

        List<ChatMessage> allMessages = chatMessageRepository.findAll();
        Map<Long, Map<String, Object>> partnerMap = new LinkedHashMap<>();


        for (ChatMessage m : allMessages) {
            if (m.getSenderId() == null || m.getReceiverId() == null) continue;

            Users partner = null;
            if (m.getSenderId().getUserid().equals(loggedUser.getUserid())) {
                partner = m.getReceiverId();
            } else if (m.getReceiverId().getUserid().equals(loggedUser.getUserid())) {
                partner = m.getSenderId();
            }

            if (partner != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", partner.getUserid());
                data.put("name", (partner.getNume() != null ? partner.getNume() : "") + " " + (partner.getPrenume() != null ? partner.getPrenume() : ""));
                data.put("lastMsg", m.getContent());
                data.put("time", m.getSentAt() != null ? m.getSentAt().toLocalTime().toString().substring(0, 5) : "");
                partnerMap.put(partner.getUserid(), data);
            }
        }

        return ResponseEntity.ok(new ArrayList<>(partnerMap.values()));
    }
}