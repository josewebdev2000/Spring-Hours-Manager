package com.hoursmanager.HoursManager.controllers;


import com.hoursmanager.HoursManager.enums.RedirectUrl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController
{
    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response)
    {
        // Invalidate the session
        session.invalidate();

        // Remove JSESSION Cookie from the browser
        Cookie jSessionCookie = new Cookie("JSESSIONID", null);
        jSessionCookie.setPath("/");
        jSessionCookie.setHttpOnly(true);
        jSessionCookie.setMaxAge(0);
        response.addCookie(jSessionCookie);

        // Redirect the user home
        return String.format("redirect:%s", RedirectUrl.HOME.getRedirectUrl());

    }
}
