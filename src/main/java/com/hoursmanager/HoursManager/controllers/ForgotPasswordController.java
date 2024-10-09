package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.forms.ForgotPasswordInfo;
import com.hoursmanager.HoursManager.models.PasswordResetToken;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repoImp.PasswordResetTokenRepoImp;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.services.EmailService;
import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import com.hoursmanager.HoursManager.views.JsonPayloadErrorResponse;
import com.hoursmanager.HoursManager.views.JsonPayloadSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@AllArgsConstructor
public class ForgotPasswordController
{
    @Autowired
    public EmailService emailService;

    @Autowired
    public SpringUserRepository springUserRepository;

    @Autowired
    public PasswordResetTokenRepoImp passwordResetTokenRepoImp;

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model, HttpServletRequest request)
    {
        model.addAttribute("isUserLoggedIn", false);
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "Forgot Password");
        model.addAttribute("pageDescription", "Hours Manager Web Application Forgot Password Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, forgot password");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/forgot-password");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "forgot-password";
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> sendForgotPasswordEmail(@RequestBody ForgotPasswordInfo forgotPasswordInfo)
    {
        // Grab the email send by the user
        String springUserEmail = forgotPasswordInfo.getEmail();

        // Grab a user from that email
        SpringUser springUser = springUserRepository.findSpringUserBySpringUserEmail(springUserEmail);

        // If the user could not be found, return a response about that
        if (springUser == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JsonPayloadErrorResponse("No user of email " + springUserEmail + " could be found").getPayload());
        }

        // Create a new token for the user
        String passwordResetToken = passwordResetTokenRepoImp.createPasswordResetToken(springUser.getSpringUserId());

        // Build the link so the user can reset his/her password
        String resetPasswordResetLink = UrlUtils.getBaseUrl() + "/reset-password?reset_password_token=" + passwordResetToken;

        try
        {
            // Send the forgot password email
            emailService.sendForgotPasswordEmailToUser(
                    forgotPasswordInfo,
                    resetPasswordResetLink
            );

            return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadSuccessResponse("Email to reset your password was sent successfully").getPayload());
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse("Could not send email to reset password").getPayload());
        }
    }

}
