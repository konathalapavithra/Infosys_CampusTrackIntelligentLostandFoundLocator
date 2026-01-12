package com.campus.connect.service;

import com.campus.connect.model.ChatMessage;
import com.campus.connect.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository repo;

    public ChatService(ChatRepository repo) {
        this.repo = repo;
    }

    /**
     * Updated to handle private messaging requirements.
     */
    public ChatMessage saveMessage(Long itemId, String senderEmail, String message, String receiverEmail) {
        ChatMessage chat = new ChatMessage();

        chat.setItemId(itemId);
        chat.setSenderEmail(senderEmail);
        chat.setMessage(message);
        chat.setReceiverEmail(receiverEmail); // Added to support findActiveChatPartners query
        chat.setTimestamp(LocalDateTime.now());
        chat.setRecalled(false); // Ensure new messages are not flagged as recalled

        return repo.save(chat);
    }

    public List<ChatMessage> getMessages(Long itemId) {
        // Fetches item-based chat history ordered by time
        return repo.findByItemIdOrderByTimestampAsc(itemId);
    }
}