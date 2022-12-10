package example.blogsite.models;

import example.blogsite.helpers.TimeAgo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue
    private Long id;
    private String content;
    private Long repliedToId;
    private LocalDateTime commentedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public String getTimeAgo() {
        return TimeAgo.getTimeAgo(this.commentedAt);
    }
}
