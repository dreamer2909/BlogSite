package example.blogsite.helpers.validators;

import example.blogsite.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectPasswordValidator implements ConstraintValidator<CorrectPassword, String> {
    @Override
    public void initialize(CorrectPassword correctPassword) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User user = (User) authentication.getPrincipal();
            return bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
