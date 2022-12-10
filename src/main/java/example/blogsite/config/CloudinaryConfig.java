package example.blogsite.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dngrea4s2",
                "api_key", "489885664146234",
                "api_secret", "tTwvUezuO4ZUWf1pxFXvdstk0jw",
                "secure", true));
    }
}
