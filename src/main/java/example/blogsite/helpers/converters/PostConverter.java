package example.blogsite.helpers.converters;

import example.blogsite.models.Post;
import example.blogsite.repositories.PostRepository;
import org.springframework.core.convert.converter.Converter;

public class PostConverter implements Converter<Long, Post> {
    private final PostRepository postRepository;

    public PostConverter(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post convert(Long id) {
        return postRepository.getReferenceById(id);
    }
}
