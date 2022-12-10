package example.blogsite.services;

import example.blogsite.DTOs.CommentForm;
import example.blogsite.models.Comment;
import example.blogsite.models.Post;

import java.util.List;

public interface ICommentService {
    List<Comment> getAllByPost(Post post);
    Comment comment(CommentForm form);
}
