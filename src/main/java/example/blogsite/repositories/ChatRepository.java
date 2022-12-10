package example.blogsite.repositories;

import example.blogsite.helpers.compositeIds.ChatId;
import example.blogsite.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, ChatId> {
//    @Query("SELECT c FROM Chat c WHERE c.id = ?1")
    Optional<Chat> findChatByUserAIdAndUserBId(Long userAId, Long userBId);
}
