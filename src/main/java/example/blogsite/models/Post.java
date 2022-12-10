package example.blogsite.models;

import example.blogsite.helpers.TimeAgo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Post {
    @Id @GeneratedValue
    private Long id;
    private String title;
    @Column(unique = true)
    private String uniqueTitle;
    private String summary;
    private LocalDateTime createdAt;
    @Lob
    private String content;
    private String imageUrl;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

//    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
//    private Set<User> users;

    public String getTimeAgo() {
        return TimeAgo.getTimeAgo(this.createdAt);
    }
}
