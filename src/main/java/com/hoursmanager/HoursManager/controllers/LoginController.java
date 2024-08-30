package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController
{
    // GET Route for Login Page (login.html)
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request)
    {
        // Add Dynamic data to template
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "Login");
        model.addAttribute("pageDescription", "Hours Manager Web Application Login Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, login, authentication");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/login");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "login";
    }

}
