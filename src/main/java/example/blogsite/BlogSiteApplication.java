package example.blogsite;

import example.blogsite.models.*;
import example.blogsite.repositories.CategoryRepository;
import example.blogsite.repositories.ChatRepository;
import example.blogsite.repositories.PostRepository;
import example.blogsite.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@Transactional
public class BlogSiteApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(BlogSiteApplication.class, args);
    }

    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
        registry.addViewController("/register");
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository,
                                    CategoryRepository categoryRepository,
                                    PostRepository postRepository,
                                    ChatRepository chatRepository,
                                    PasswordEncoder encoder) {
        return args -> {
            User user = new User();
            user.setUsername("username");
            user.setProfileName("username");
            user.setPassword(encoder.encode("password"));
            user.setRoles("USER");
            user.setDob(LocalDate.of(2000, 1, 1));
            user.setGender("Nam");
            String cloudinaryUrlImage = "https://res.cloudinary.com/dngrea4s2/image/upload/v1668508358/";
            user.setAvatar(cloudinaryUrlImage + "cld-sample");
            userRepository.save(user);

            User user1 = new User();
            user1.setUsername("username1");
            user1.setProfileName("username1");
            user1.setPassword(encoder.encode("12345678"));
            user1.setRoles("USER");
            user1.setDob(LocalDate.of(2000, 1, 1));
            user1.setGender("Nam");
            user1.setAvatar(cloudinaryUrlImage + "cld-sample");
            userRepository.save(user1);

            categoryRepository.save(new Category(CategoryEnum.BOOK));
            categoryRepository.save(new Category(CategoryEnum.COMPOSE));
            categoryRepository.save(new Category(CategoryEnum.FASHION));
            categoryRepository.save(new Category(CategoryEnum.FOOD));
            categoryRepository.save(new Category(CategoryEnum.GAME));
            categoryRepository.save(new Category(CategoryEnum.MUSIC));
            categoryRepository.save(new Category(CategoryEnum.OPINION_ARGUMENT));
            categoryRepository.save(new Category(CategoryEnum.PHOTOGRAPHY));
            categoryRepository.save(new Category(CategoryEnum.SCIENCE_TECHNOLOGY));
            categoryRepository.save(new Category(CategoryEnum.SPORT));
            categoryRepository.save(new Category(CategoryEnum.THINKING_OUT_LOUD));
            //categoryRepository.save(new Category(CategoryEnum.TRAVEL));
            Category category = new Category(CategoryEnum.TRAVEL);
            categoryRepository.save(category);

            Post post = new Post();
            post.setUser(user);
            post.setCategory(category);
            post.setCreatedAt(LocalDateTime.now());
            post.setTitle("title");
            post.setUniqueTitle("title");
            post.setContent("content");
            post.setImageUrl("https://res.cloudinary.com/dngrea4s2/image/upload/v1668508358/");
            postRepository.save(post);

//            Chat chat = new Chat();
//            chat.setUserAId(1L);
//            chat.setUserBId(2L);
//            Message message = new Message();
//            message.setMessage("hello");
//            message.setFromId(1L);
//            message.setFrom(userRepository.getReferenceById(1L));
//            message.setTo(userRepository.getReferenceById(2L));
//            message.setToId(2L);
//            message.setSendAt(LocalDateTime.now());
//            message.setChat(chat);
//            chat.addMessage(message);
//            chatRepository.save(chat);
        };
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
}
