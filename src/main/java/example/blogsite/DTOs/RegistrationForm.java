package example.blogsite.DTOs;

import example.blogsite.helpers.validators.SecondGroup;
import example.blogsite.helpers.validators.UniqueUsername;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class RegistrationForm {
    @NotBlank(message = "Tên hiển thị không được trống")
    @Length(min = 5, max = 20, message = "Tên hiển thị phải ít nhất 5 ký tự và không quá 20 ký tự", groups = SecondGroup.class)
    @UniqueUsername(message = "Tên tài khoản đã tồn tại")
    String username;
    @NotEmpty(message = "Mật khẩu không được để trống")
    @Length(min = 5, max = 20, message = "Mật khẩu phải ít nhất 5 ký tự và không quá 20 ký tự", groups = SecondGroup.class)
    String password;
    String confirmedPassword;
}
