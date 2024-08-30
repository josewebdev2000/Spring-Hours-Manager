package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController
{
    // Get Route for Register Page (register.html)
    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request)
    {
        // Add Dynamic data to template
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "Register");
        model.addAttribute("pageDescription", "Hours Manager Web Application Register Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, registration, register");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/register");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "register";
    }
}
