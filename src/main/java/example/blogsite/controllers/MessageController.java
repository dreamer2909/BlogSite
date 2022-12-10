package example.blogsite.controllers;

import example.blogsite.DTOs.MessageDTO;
import example.blogsite.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, ChatService chatService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/ws")
    public void send(MessageDTO message) {
        chatService.saveChat(message);
        simpMessagingTemplate.convertAndSend("/topic/chat", message);
    }
}
