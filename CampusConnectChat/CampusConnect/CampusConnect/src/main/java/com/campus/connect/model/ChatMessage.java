package com.campus.connect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemId;
    private String senderEmail;
    private String receiverEmail;

    @Column(length = 2000)
    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * FEATURE: Soft Delete/Recall
     * When true, the UI displays a "This message was recalled" notice.
     */
    private boolean recalled = false;

    public ChatMessage() {}

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getReceiverEmail() { return receiverEmail; }
    public void setReceiverEmail(String receiverEmail) { this.receiverEmail = receiverEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public boolean isRecalled() { return recalled; }
    public void setRecalled(boolean recalled) { this.recalled = recalled; }
}