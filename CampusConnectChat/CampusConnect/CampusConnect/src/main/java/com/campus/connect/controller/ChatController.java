package com.campus.connect.controller;

import com.campus.connect.model.ChatMessage;
import com.campus.connect.service.ChatService;
import com.campus.connect.repository.ChatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Crucial for Milestone 4 frontend-backend communication
public class ChatController {

    private final ChatService chatService;
    private final ChatRepository chatRepository;

    public ChatController(ChatService chatService, ChatRepository chatRepository) {
        this.chatService = chatService;
        this.chatRepository = chatRepository;
    }

    /**
     * SEND MESSAGE: Uses ChatService to save message with 4 arguments.
     */
    // In ChatController.java
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody Map<String, String> payload) {
        try {
            // itemId must be present in the JSON
            Long itemId = Long.parseLong(payload.get("itemId").toString());
            String sender = payload.get("senderEmail");
            String msg = payload.get("message");
            String receiver = payload.get("receiverEmail");

            ChatMessage saved = chatService.saveMessage(itemId, sender, msg, receiver);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace(); // This will show the error in your IntelliJ console
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET PARTNERS: Fetches unique participants for the chat-list dashboard.
     * This is the endpoint currently failing in your screenshot.
     */
    @GetMapping("/partners")
    public ResponseEntity<List<String>> getChatPartners(@RequestParam String email) {
        try {
            List<String> partners = chatRepository.findActiveChatPartners(email);
            return ResponseEntity.ok(partners != null ? partners : new ArrayList<>());
        } catch (Exception e) {
            // Logs error to IntelliJ console to help diagnose MySQL connection issues
            System.err.println("MySQL Error in getChatPartners: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * GET HISTORY: Fetches private history between two specific users.
     */
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getHistory(
            @RequestParam String user1,
            @RequestParam String user2) {
        List<ChatMessage> conversation = chatRepository.findConversation(user1, user2);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/item/{itemId}")
    public List<ChatMessage> getMessagesByItem(@PathVariable Long itemId) {
        return chatRepository.findByItemIdOrderByTimestampAsc(itemId);
    }

    /**
     * FEATURE: RECALL MESSAGE (Soft Delete).
     */
    @PutMapping("/recall/{id}")
    public ResponseEntity<ChatMessage> recallMessage(@PathVariable Long id, @RequestParam String email) {
        Optional<ChatMessage> msgOpt = chatRepository.findById(id);

        if (msgOpt.isPresent()) {
            ChatMessage msg = msgOpt.get();
            if (msg.getSenderEmail().equals(email)) {
                msg.setMessage("This message was recalled");
                msg.setRecalled(true);
                ChatMessage updated = chatRepository.save(msg);
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * FEATURE: DELETE MESSAGE (Hard Delete).
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id, @RequestParam String email) {
        try {
            chatRepository.deleteByIdAndSenderEmail(id, email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}