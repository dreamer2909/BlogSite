package example.blogsite.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import example.blogsite.DTOs.PostForm;
import example.blogsite.helpers.VNCharacterUtils;
import example.blogsite.models.Post;
import example.blogsite.models.Tag;
import example.blogsite.models.User;
import example.blogsite.repositories.PostRepository;
import example.blogsite.repositories.TagRepository;
import example.blogsite.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findPostsByUserId(userId);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Post savePost(PostForm form) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        post.setContent(form.getContent());
        post.setSummary(form.getSummary());
        post.setCategory(form.getCategory());

        Set<Tag> tags = form.getTags();
        if (tags != null) {
            tags.forEach(tag -> tag.setTitle(tag.getTitle().strip().toUpperCase()));
            tags.removeIf(t -> tagRepository.findAllByTitle(t.getTitle()).isPresent() || t.getTitle().isBlank());
            post.setTags(tags);
        }

        if (!form.getImage().isEmpty()) {
            var imageUpload = cloudinary.uploader().upload(form.getImage().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            post.setImageUrl((String) imageUpload.get("secure_url"));
        }

        // generate unique title
        String uniqueTitle = VNCharacterUtils.removeAccent(form.getTitle());
        String randomStr = UUID.randomUUID().toString().replaceAll("_", "");
        while (postRepository.findByUniqueTitle(uniqueTitle + randomStr).isPresent()) {
            randomStr = UUID.randomUUID().toString().replaceAll("_", "").replaceAll("_", "");
        }

        post.setUniqueTitle(uniqueTitle + "-" + randomStr);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> getFoundPosts(String keyword) {
        String upperCaseKeyword = VNCharacterUtils.removeAccent(keyword.toLowerCase().trim());
        return postRepository.findAll()
                .stream()
                .filter(post -> post.getTitle().toUpperCase().contains(upperCaseKeyword)
                        || post.getTags().stream().anyMatch(tag -> tag.getTitle().contains(upperCaseKeyword)))
                .toList();
    }

    @Override
    public Post getByUniqueTitle(String title) throws Exception {
        Optional<Post> post = postRepository.findByUniqueTitle(title);
        return post.orElseThrow(() -> new Exception("Unique title " + title + " not found"));
    }

    @Override
    public List<Post> getPostsByCategoryId(Long id) {
        return postRepository.findAllByCategoryId(id);
    }

    @Override
    public void likePost(Long postId) {
        Post post = postRepository.getReferenceById(postId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (isLike(postId)) {
            getLikedPosts(user.getId()).remove(post);
            return;
        }

        getUnlikedPosts(user.getId()).remove(post);
        getLikedPosts(user.getId()).add(post);
    }

    @Override
    public boolean isLike(Long postId) {
        Post post = postRepository.getReferenceById(postId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getLikedPosts(user.getId()).contains(post);
    }

    @Override
    public void unlikePost(Long postId) {
        Post post = postRepository.getReferenceById(postId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (isUnlike(postId)) {
            getUnlikedPosts(user.getId()).remove(post);
            return;
        }

        getLikedPosts(user.getId()).remove(post);
        getUnlikedPosts(user.getId()).add(post);
    }

    @Override
    public boolean isUnlike(Long postId) {
        Post post = postRepository.getReferenceById(postId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUnlikedPosts(user.getId()).contains(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.getReferenceById(id);
    }


    public Set<Post> getLikedPosts(long userId) {
        User user = userRepository.getReferenceById(userId);
        return user.getLikedPosts();
    }

    public Set<Post> getUnlikedPosts(long userId) {
        User user = userRepository.getReferenceById(userId);
        return user.getUnlikedPosts();
    }
}
