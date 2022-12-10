package example.blogsite.helpers.converters;

import example.blogsite.models.User;
import example.blogsite.repositories.UserRepository;
import org.springframework.core.convert.converter.Converter;

public class UserConverter implements Converter<Long, User> {
    private final UserRepository userRepository;

    public UserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User convert(Long id) {
        return userRepository.getReferenceById(id);
    }
}
