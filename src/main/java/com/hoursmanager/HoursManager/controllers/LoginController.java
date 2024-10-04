package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.dto.SpringUserDto;
import com.hoursmanager.HoursManager.enums.RedirectUrl;
import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.forms.LoginInfo;
import com.hoursmanager.HoursManager.repoImp.SpringUserRepoImp;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import com.hoursmanager.HoursManager.views.JsonPayloadErrorResponse;
import com.hoursmanager.HoursManager.views.JsonPayloadRedirectUrl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Controller
@AllArgsConstructor
public class LoginController
{
    private final SpringUserRepoImp springUserRepoImp;

    // GET Route for Login Page (login.html)
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpSession session)
    {
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) == null)
        {
            // Add Dynamic data to template
            model.addAttribute("isUserLoggedIn", false);
            model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
            model.addAttribute("pageTitle", "Login");
            model.addAttribute("pageDescription", "Hours Manager Web Application Login Page");
            model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, login, authentication");
            model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/login");
            model.addAttribute("year", TimeUtils.getCurrentYear());
            model.addAttribute("request", request);

            return "login";
        }

        else
        {
            return String.format("redirect:%s", RedirectUrl.DASHBOARD.getRedirectUrl());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginExistingSpringUser(@RequestBody LoginInfo loginInfo, HttpSession session, UriComponentsBuilder uriBuilder)
    {
        // Try to log in an existing user
        try
        {
            // Grab user Data
            SpringUserDto loggedInUser = springUserRepoImp.loginUser(loginInfo);

            // Store the user ID in the session
            session.setAttribute(SessionAttribute.USER_ID.getKey(), loggedInUser.getId());

            // Prepare the redirect URI
            String dashboardUri = uriBuilder.path(RedirectUrl.DASHBOARD.getRedirectUrl()).build().toUriString();
            return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadRedirectUrl(dashboardUri).getPayload());
        }

        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
        }
    }

}
