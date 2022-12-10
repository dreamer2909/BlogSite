package example.blogsite.services;

import example.blogsite.DTOs.PostForm;
import example.blogsite.models.Post;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<Post> getPostsByUserId(Long userId);
    List<Post> getAll();
    Post savePost(PostForm form) throws IOException;
    List<Post> getFoundPosts(String keyword);
    Post getByUniqueTitle(String title) throws Exception;
    List<Post> getPostsByCategoryId(Long id);
    void likePost(Long postId);
    boolean isLike(Long postId);
    void unlikePost(Long postId);
    boolean isUnlike(Long postId);
    Post getPostById(Long id);
}
