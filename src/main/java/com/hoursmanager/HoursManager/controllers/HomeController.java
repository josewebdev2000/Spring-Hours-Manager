package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.config.AppConfig;
import com.hoursmanager.HoursManager.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @Autowired
    private AppConfig appConfig;

    // Set GET Route for Home Page (index.html)
    @GetMapping("/")
    public String index(Model model)
    {
        // Add Dynamic data to template
        model.addAttribute("baseUrl", appConfig.getBaseUrl());
        model.addAttribute("pageTitle", "Home");
        model.addAttribute("pageDescription", "Hours Manager Web Application Home Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager");
        model.addAttribute("pageUrl", appConfig.getBaseUrl() + "/");
        model.addAttribute("year", TimeUtils.getCurrentYear());

        return "index";
    }
}
