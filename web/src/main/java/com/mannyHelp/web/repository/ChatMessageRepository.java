package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE " +
            "(m.senderId.userid = :u1 AND m.receiverId.userid = :u2) OR " +
            "(m.senderId.userid = :u2 AND m.receiverId.userid = :u1) " +
            "ORDER BY m.sentAt ASC")
    List<ChatMessage> findConversation(@Param("u1") Long user1Id, @Param("u2") Long user2Id);


    @Query("SELECT m.senderId.userid FROM ChatMessage m WHERE m.receiverId.userid = :userId ORDER BY m.sentAt DESC LIMIT 1")
    Long findLastChatPartnerUserId(@Param("userId") Long userId);

   
    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.receiverId.userid = :userId")
    long countUnreadMessagesByUserId(@Param("userId") Long userId);
}