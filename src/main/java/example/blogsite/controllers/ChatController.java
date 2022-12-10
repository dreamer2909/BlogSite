package example.blogsite.controllers;

import example.blogsite.models.Chat;
import example.blogsite.models.Message;
import example.blogsite.models.User;
import example.blogsite.services.IChatService;
import example.blogsite.services.IUserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final IChatService chatService;
    private final IUserService userService;

    public ChatController(IChatService chatService, IUserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String chat(@PathVariable String username, Model model) {
        User userA = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userB = userService.getUserByUsername(username);

        Chat chat = chatService.getById(userA.getId(), userB.getId());

        model.addAttribute("user", userB);
        model.addAttribute("messages", chat.getMessages());
        return "chat";
    }
}
