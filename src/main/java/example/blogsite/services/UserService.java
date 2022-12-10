package example.blogsite.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import example.blogsite.DTOs.AccountSettingForm;
import example.blogsite.DTOs.ProfileSettingForm;
import example.blogsite.DTOs.RegistrationForm;
import example.blogsite.DTOs.ResetPasswordForm;
import example.blogsite.helpers.constants.CloudinaryConstants;
import example.blogsite.helpers.constants.UserConstants;
import example.blogsite.models.User;
import example.blogsite.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final Cloudinary cloudinary;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.cloudinary = cloudinary;
    }

    @Override
    public User createUser(RegistrationForm form) {
        // set default info
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(encoder.encode(form.getPassword()));
        user.setProfileName(user.getUsername());
        user.setRoles(UserConstants.ROLE_USER);
        user.setJoinedAt(UserConstants.JOINED_AT);
        user.setDob(UserConstants.DEFAULT_DOB);
        user.setAvatar(CloudinaryConstants.DEFAULT_AVATAR_URL);
        return userRepository.save(user);
    }

    @Override
    public User changePassword(ResetPasswordForm form) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(encoder.encode(form.getNewPassword()));
        return userRepository.save(user);
    }

    @Override
    public Set<User> getFollowers(long userId) {
        User user = userRepository.getReferenceById(userId);
        return user.getFollowers();
    }

    @Override
    public Set<User> getFollowing(long userId) {
        User user = userRepository.getReferenceById(userId);
        return user.getFollowing();
    }

    @Override
    public boolean isFollow(long userId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User toFollow = userRepository.getReferenceById(userId);
        return getFollowing(user.getId()).contains(toFollow);
    }

    @Override
    public void follow(String username) {
        User follower = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User toFollow = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        getFollowing(follower.getId()).add(toFollow);
        getFollowers(toFollow.getId()).add(follower);
    }

    @Override
    public void unfollow(String username) {
        User follower = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User toFollow = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        getFollowing(follower.getId()).remove(toFollow);
        getFollowers(toFollow.getId()).remove(follower);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    @Override
    public User updateAccount(AccountSettingForm form) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setProfileName(form.getProfileName());
        user.setGender(form.getGender());
        user.setDescription(form.getDescription());
        user.setLocation(form.getLocation());

        if (!form.getAvatarImage().isEmpty()) {
                var avatarUpload = cloudinary.uploader().upload(form.getAvatarImage().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar((String) avatarUpload.get("secure_url"));
        }
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(ProfileSettingForm form) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setEducation(form.getEducation());
        user.setJob(form.getJob());
        user.setHometown(form.getHometown());
        return userRepository.save(user);
    }

    public boolean isUsernameExists(String username) {
        return !userRepository.findByUsername(username).isEmpty();
    }
}
