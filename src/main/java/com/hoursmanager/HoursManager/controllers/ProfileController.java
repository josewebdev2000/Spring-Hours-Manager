package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.enums.RedirectUrl;
import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.StringFormattingUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dashboard")
@Controller
@AllArgsConstructor
public class ProfileController
{
    private final SpringUserRepository springUserRepository;

    // GET Route for Profile Page (profile.html)
    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request, HttpSession session)
    {
        // Check the user is logged in
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) == null)
        {
            return String.format("redirect:%s", RedirectUrl.HOME.getRedirectUrl());
        }

        else if (!DbUtils.isUserIdInDb((Long) session.getAttribute(SessionAttribute.USER_ID.getKey()), springUserRepository))
        {
            return String.format("redirect:%s", RedirectUrl.HOME.getRedirectUrl());
        }

        else
        {
            // Grab the user's ID from the current session
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());

            // Grab the current user
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the username
            String springUserName = springUser.getSpringUserName();

            // Grab the email address
            String springUserEmail = springUser.getSpringUserEmail();

            // Grab the user's profile picture
            String springUserPicUrl = DbUtils.getUserProfilePic(userId, springUserRepository, "/img/avatars/user.png");

            // Add Dynamic data to the template
            model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
            model.addAttribute("pageTitle", "User Profile");
            model.addAttribute("pageDescription", "Hours Manager Web Application User Profile");
            model.addAttribute("pageKeywords", "working, hours, tracker, user, profile, settings, password change, forgot password");
            model.addAttribute("springUserName", springUserName);
            model.addAttribute("springUserPossessiveName", StringFormattingUtils.getPossessiveNounFromName(springUserName));
            model.addAttribute("springUserEmail", springUserEmail);
            model.addAttribute("springUserPicUrl", springUserPicUrl);
            model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/dashboard/profile");
            model.addAttribute("request", request);

            return "profile";

        }
    }

    // Make POST Routes to modify username, password, and avatar pic
    /*
    @PostMapping("/profile/usernameUpdate")
    @PostMapping("/profile/avatarUpdate")
    @PostMapping("/profile/passwordUpdate")
     */

}
