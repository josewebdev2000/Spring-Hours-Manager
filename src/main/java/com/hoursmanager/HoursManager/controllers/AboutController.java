package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.SessionUtils;
import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AboutController
{
    private final SpringUserRepository springUserRepository;

    // GET Route for About Page (about.html)
    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request, HttpSession session)
    {
        // Determine if the user is logged in
        boolean isUserLoggedIn = SessionUtils.isSessionValueValid(session, "userId");

        // If the user is logged in
        if (isUserLoggedIn)
        {
            // Grab the user's ID
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());

            // Grab the actual user
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the username
            String springUserName = springUser.getSpringUserName();

            // Grab the profile picture
            String springUserProfilePic = DbUtils.getUserProfilePic(userId, springUserRepository, "/img/avatars/user.png");

            // Add that to the model
            model.addAttribute("springUserName", springUserName);
            model.addAttribute("springUserPicUrl", springUserProfilePic);
        }

        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "About");
        model.addAttribute("pageDescription", "Hours Manager Web Application About Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, about");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/about");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "about";
    }
}
