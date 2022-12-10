package example.blogsite.controllers;

import example.blogsite.DTOs.CommentForm;
import example.blogsite.DTOs.PostForm;
import example.blogsite.models.Post;
import example.blogsite.services.ICategoryService;
import example.blogsite.services.ICommentService;
import example.blogsite.services.IPostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {
    private final IPostService postService;
    private final ICategoryService categoryService;
    private final ICommentService commentService;

    @GetMapping("/editor")
    public String editor(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "editor";
    }

    @PostMapping("/editor")
    public String editor(PostForm form) throws IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return "redirect:/login";

        postService.savePost(form);
        return "redirect:/editor";
    }

    @GetMapping("/post/{title}")
    public String post(@PathVariable String title, Model model) throws Exception {
        Post post = postService.getByUniqueTitle(title);
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.getAllByPost(post));
        model.addAttribute("comment", new CommentForm());
        model.addAttribute("liked", postService.isLike(post.getId()));
        model.addAttribute("unliked", postService.isUnlike(post.getId()));
        return "post";
    }

    @PostMapping("/post/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Post> posts = postService.getFoundPosts(keyword);
        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword);
        return "search";
    }

    @PostMapping("/post/like")
    public String like(@RequestParam Long postId) {
        postService.likePost(postId);
        Post post = postService.getPostById(postId);
        return "redirect:/post/" + post.getUniqueTitle();
    }

    @PostMapping("/post/unlike")
    public String unlike(@RequestParam Long postId) {
        postService.unlikePost(postId);
        Post post = postService.getPostById(postId);
        return "redirect:/post/" + post.getUniqueTitle();
    }
}
