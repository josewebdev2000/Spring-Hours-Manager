package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController
{
    // GET Route for About Page (about.html)
    @GetMapping("/about")
    public String about(Model model, HttpServletRequest request)
    {
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
