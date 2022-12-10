package example.blogsite.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter @Setter
public class AccountSettingForm {
        String profileName;
        String gender;
        String description;
        String location;
        LocalDate dob;
        MultipartFile avatarImage;
}
