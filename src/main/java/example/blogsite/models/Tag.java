package example.blogsite.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Tag {
    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String title;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Post> posts;

    public Tag(String title) {
        this.title = title;
    }
}
