package example.blogsite.helpers.compositeIds;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;
@NoArgsConstructor
public class ChatId implements Serializable {
    @Column(name = "userAId")
    private Long userAId;
    @Column(name = "userBId")
    private Long userBId;

    public ChatId(Long userAId, Long userBId) {
        this.userAId = userAId;
        this.userBId = userBId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ChatId) {
            return Objects.equals(this.userAId, ((ChatId) o).userAId) && Objects.equals(this.userBId, ((ChatId) o).userBId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAId, userBId);
    }
}
