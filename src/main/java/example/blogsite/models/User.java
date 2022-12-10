package example.blogsite.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter @Setter
public class User implements UserDetails {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String roles;
    private String profileName;
    private String description;
    private String gender;
    private String profile;
    private String job;
    private String education;
    private String location;
    private String hometown;
    private LocalDate dob;
    private LocalDate joinedAt;
    private String avatar;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles == null ? Collections.emptyList() : Arrays.stream(roles.split(";")).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany()
    @JoinTable(name = "liked_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> likedPosts = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "unliked_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> unlikedPosts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "following")
    private Set<User> followers = new HashSet<>();

    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> following = new HashSet<>();
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Chat> chats;
}
