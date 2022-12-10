package example.blogsite.DTOs;

import example.blogsite.models.Post;
import example.blogsite.models.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CommentForm {
        @NotBlank(message = "Vui lòng nhập nhận xét")
        @Length(min = 5, max = 200, message = "Nhận xét phải ít nhất 5 ký tự và không quá 200 ký tự")
        String content;
        Long repliedToId;
        User user;
        Post post;
}
