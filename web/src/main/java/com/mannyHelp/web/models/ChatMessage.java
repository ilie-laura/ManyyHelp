package com.mannyHelp.web.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)

    private Users senderId;

    @ManyToOne(fetch = FetchType.EAGER)

    private Users receiverId;

    @Column(length = 2000, nullable = false)
    private String content;

    private LocalDateTime sentAt;
}