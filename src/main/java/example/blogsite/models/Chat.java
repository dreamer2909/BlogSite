package example.blogsite.models;

import example.blogsite.helpers.compositeIds.ChatId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@IdClass(ChatId.class)
public class Chat {
    @Id
    private Long userAId;
    @Id
    private Long userBId;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public Chat(Long userAId, Long userBId) {
        this.userAId = userAId;
        this.userBId = userBId;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
