package example.blogsite.controllers;

import example.blogsite.DTOs.CommentForm;
import example.blogsite.models.Comment;
import example.blogsite.repositories.CommentRepository;
import example.blogsite.services.ICommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/comment")
public class CommentController {
    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public String comment(CommentForm form) {
        commentService.comment(form);
        return "redirect:/post/" + form.getPost().getUniqueTitle();
    }
}
