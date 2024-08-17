package com.hoursmanager.HoursManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    // Set GET Route for Home Page (index.html)
    @GetMapping("/")
    public String index(Model model)
    {
        return "index";
    }
}
