package example.blogsite.services;

import example.blogsite.DTOs.AccountSettingForm;
import example.blogsite.DTOs.ProfileSettingForm;
import example.blogsite.DTOs.RegistrationForm;
import example.blogsite.DTOs.ResetPasswordForm;
import example.blogsite.models.User;

import java.io.IOException;
import java.util.Set;

public interface IUserService {
    User createUser(RegistrationForm form);
    User getUserByUsername(String username);
    User updateAccount(AccountSettingForm form) throws IOException;
    User updateProfile(ProfileSettingForm form);
    User changePassword(ResetPasswordForm form);
    Set<User> getFollowers(long userId);
    Set<User> getFollowing(long userId);
    boolean isFollow(long userId);
    void follow(String username);
    void unfollow(String username);
}
