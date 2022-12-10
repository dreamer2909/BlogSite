package example.blogsite.services;

import example.blogsite.DTOs.MessageDTO;
import example.blogsite.helpers.compositeIds.ChatId;
import example.blogsite.models.Chat;

import java.util.List;

public interface IChatService {
    Chat getById(Long userAId, Long userBId);
    Chat saveChat(MessageDTO message);
    Chat saveChat(Long userAId, Long userBId);
}
