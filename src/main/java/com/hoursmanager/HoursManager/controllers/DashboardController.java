package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.enums.RedirectUrl;
import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DashboardController
{
    private final SpringUserRepository springUserRepository;

    // GET Route for Dashboard Page (dashboard.html)
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request, HttpSession session)
    {
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
            // Grab the user's ID
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());

            // Grab the actual user
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the username
            String springUserName = springUser.getSpringUserName();

            // Get the user's profile picture
            String springUserProfilePic = DbUtils.getUserProfilePic(userId, springUserRepository, "/img/avatars/user.png");

            // Add Dynamic data to the template
            model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
            model.addAttribute("pageTitle", "Dashboard");
            model.addAttribute("pageDescription", "Hours Manager Web Application User Dashboard");
            model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, dashboard, control panel");
            model.addAttribute("springUserName", springUserName);
            model.addAttribute("springUserPicUrl", springUserProfilePic);
            model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/dashboard");
            model.addAttribute("request", request);

            return "dashboard";
        }

    }

}
