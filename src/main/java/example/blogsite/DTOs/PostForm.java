package example.blogsite.DTOs;

import example.blogsite.models.Category;
import example.blogsite.models.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter @Setter
public class PostForm {
        String title;
        String summary;
        String content;
        Category category;
        Set<Tag> tags;
        MultipartFile image;
}
