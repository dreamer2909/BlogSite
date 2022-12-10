package example.blogsite.services;

import example.blogsite.DTOs.CommentForm;
import example.blogsite.models.Comment;
import example.blogsite.models.Post;
import example.blogsite.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }

    @Override
    public Comment comment(CommentForm form) {
        Comment comment = new Comment();
        comment.setCommentedAt(LocalDateTime.now());
        comment.setContent(form.getContent());
        comment.setUser(form.getUser());
        comment.setPost(form.getPost());
        comment.setRepliedToId(form.getRepliedToId());
        return commentRepository.save(comment);
    }
}
