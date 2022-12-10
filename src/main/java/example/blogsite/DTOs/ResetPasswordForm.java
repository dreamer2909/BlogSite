package example.blogsite.DTOs;

import example.blogsite.helpers.validators.CorrectPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordForm {
    @CorrectPassword(message = "Mật khẩu không đúng")
    String password;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Length(min = 8, max = 20, message = "Mật khẩu phải dài ít nhất 8 ký tự và không quá 20 ký tự")
    String newPassword;

    String confirmedPassword;
}
