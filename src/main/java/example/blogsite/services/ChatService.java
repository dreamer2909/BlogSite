package example.blogsite.services;

import example.blogsite.DTOs.MessageDTO;
import example.blogsite.models.Chat;
import example.blogsite.models.Message;
import example.blogsite.repositories.ChatRepository;
import example.blogsite.repositories.MessageRepository;
import example.blogsite.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ChatService implements IChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Chat getById(Long userAId, Long userBId) {
        System.out.println(userAId);
        System.out.println(userBId);
        Chat chat;
        try {
            chat = chatRepository.findChatByUserAIdAndUserBId(userAId, userBId).get();
        } catch (NoSuchElementException e) {
            if (chatRepository.findChatByUserAIdAndUserBId(userBId, userAId).isPresent()) {
                chat = chatRepository.findChatByUserAIdAndUserBId(userBId, userAId).get();
            } else {
                System.out.println("hello");
                chat = saveChat(userAId, userBId);
            }
        }
        return chat;
    }

    @Override
    public Chat saveChat(MessageDTO message) {
        Chat chat = getById(message.getFromId(), message.getToId());
        Message m = new Message();
        m.setMessage(message.getMessage());
        m.setFromId(message.getFromId());
        m.setToId(message.getToId());
        m.setFrom(userRepository.getReferenceById(message.getFromId()));
        m.setTo(userRepository.getReferenceById(message.getToId()));
        m.setSendAt(LocalDateTime.now());
        m.setChat(chat);
        messageRepository.save(m);
        chat.addMessage(m);
        return chatRepository.save(chat);
    }

    @Override
    public Chat saveChat(Long userAId, Long userBId) {
        return chatRepository.save(new Chat(userAId, userBId));
    }
}
