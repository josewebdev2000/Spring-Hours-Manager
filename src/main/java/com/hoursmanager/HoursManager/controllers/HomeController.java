package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.enums.RedirectUrl;
import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.SessionUtils;
import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @Autowired
    private SpringUserRepository springUserRepository;

    // GET Route for Home Page (index.html)
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request, HttpSession session)
    {
        // If the userId session variable is set, then go to the dashboard
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) != null)
        {
            // Extract the user ID
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());

            // Check if a user is in the DB
            boolean userInDb = DbUtils.isUserIdInDb(userId, springUserRepository);

            if (userInDb)
            {
                return String.format("redirect:%s", RedirectUrl.DASHBOARD.getRedirectUrl());
            }

        }
        // Add Dynamic data to template
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        // Show the user is not logged in
        model.addAttribute("isUserLoggedIn", false);
        model.addAttribute("pageTitle", "Home");
        model.addAttribute("pageDescription", "Hours Manager Web Application Home Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "index";
    }
}
