package example.blogsite.controllers;

import example.blogsite.DTOs.AccountSettingForm;
import example.blogsite.DTOs.ProfileSettingForm;
import example.blogsite.DTOs.ResetPasswordForm;
import example.blogsite.models.User;
import example.blogsite.services.IPostService;
import example.blogsite.services.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final IUserService userService;
    private final IPostService postService;

    public UserController(IUserService userService, IPostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/_{username}")
    public String profile(@PathVariable String username, Model model) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("posts", postService.getPostsByUserId(user.getId()));
        model.addAttribute("following", userService.getFollowing(user.getId()));
        model.addAttribute("followers", userService.getFollowers(user.getId()));
        model.addAttribute("isFollow", userService.isFollow(user.getId()));
        return "profile";
    }

    @GetMapping("/setting")
    public String accountSettingPage() {
        return "account-setting";
    }

    @PostMapping("/setting-account")
    public String setting(AccountSettingForm form) throws IOException {
        userService.updateAccount(form);
        return "redirect:/user/setting";
    }

    @GetMapping("/change-password")
    public String showRegisterForm(Model model) {
        model.addAttribute("resetPasswordForm", new ResetPasswordForm());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(Model model, @Valid ResetPasswordForm form, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("changePasswordError", true);
            return "change-password";
        }
        userService.changePassword(form);
        return "redirect:/user/change-password";
    }

    @GetMapping("/profile-setting")
    public String profileSettingPage() {
        return "profile-setting";
    }

    @PostMapping("/profile-setting")
    public String profileSetting(ProfileSettingForm form) {
        userService.updateProfile(form);
        return "redirect:/user/profile-setting";
    }

    @PostMapping("/follow")
    public String follow(@RequestParam String username) {
        userService.follow(username);
        return "redirect:/user/" + username;
    }

    @PostMapping("/unfollow")
    public String unfollow(@RequestParam String username) {
        userService.unfollow(username);
        return "redirect:/user/" + username;
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat-list";
    }
}
