package net.j7.ebook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatMessage {

    private Long id;
    public String message;
    public String author;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;

    public ChatMessage() {
    }

    public ChatMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }
}

