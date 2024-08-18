package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivacyController
{
    // GET Route for Privacy Page (privacy.html)
    @GetMapping("/privacy")
    public String privacy(Model model, HttpServletRequest request)
    {
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "Privacy");
        model.addAttribute("pageDescription", "Hours Manager Web Application Privacy Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, privacy statement, privacy");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/privacy");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "privacy";
    }
}
