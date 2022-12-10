package example.blogsite.controllers;

import example.blogsite.models.Post;
import example.blogsite.services.ICategoryService;
import example.blogsite.services.IPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final IPostService postService;
    private final ICategoryService categoryService;

    public CategoryController(IPostService postService, ICategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String category(@PathVariable Long id, Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("category", categoryService.getById(id));
        List<Post> posts = postService.getPostsByCategoryId(id);
        model.addAttribute("posts", posts);
        return "category";
    }
}
