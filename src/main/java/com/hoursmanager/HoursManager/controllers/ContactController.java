package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController
{
    @GetMapping("/contact")
    public String contact(Model model, HttpServletRequest request)
    {
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "Contact");
        model.addAttribute("pageDescription", "Hours Manager Web Application Contact Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, contact page, contact us");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/contact");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "contact";
    }
}
