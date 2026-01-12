package com.campus.connect.repository;

import com.campus.connect.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * Fetches messages belonging to a specific item.
     * Used for general item-based chat threads.
     */
    List<ChatMessage> findByItemIdOrderByTimestampAsc(Long itemId);

    /**
     * SPECIFIC QUERY FOR TWO-PERSON CONVERSATION
     * Fetches the bidirectional history between two users.
     */
    @Query("SELECT c FROM ChatMessage c WHERE " +
            "(c.senderEmail = ?1 AND c.receiverEmail = ?2) OR " +
            "(c.senderEmail = ?2 AND c.receiverEmail = ?1) " +
            "ORDER BY c.timestamp ASC")
    List<ChatMessage> findConversation(String user1, String user2);

    /**
     * FEATURE: HARD DELETE
     * Ensures a user can only delete their own message record from MySQL.
     * Transactional and Modifying are required for DML operations.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM ChatMessage c WHERE c.id = ?1 AND c.senderEmail = ?2")
    void deleteByIdAndSenderEmail(Long id, String senderEmail);

    /**
     * FEATURE: CHAT PARTNER LIST
     * Uses a Native SQL UNION to find all unique emails the user has messaged.
     * Required for populating chat-list.html.
     */
    @Query(value = "SELECT DISTINCT receiver_email FROM chat_messages WHERE sender_email = ?1 " +
            "UNION " +
            "SELECT DISTINCT sender_email FROM chat_messages WHERE receiver_email = ?1",
            nativeQuery = true)
    List<String> findActiveChatPartners(String email);
}