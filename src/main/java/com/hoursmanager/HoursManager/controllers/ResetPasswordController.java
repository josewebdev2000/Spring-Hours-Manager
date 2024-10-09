package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.exceptions.DbException;
import com.hoursmanager.HoursManager.exceptions.EmailServiceException;
import com.hoursmanager.HoursManager.exceptions.InvalidResetPasswordToken;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.ResetPasswordInfo;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repoImp.PasswordResetTokenRepoImp;
import com.hoursmanager.HoursManager.repoImp.SpringUserRepoImp;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ResetPasswordController
{
    @Autowired
    public EmailService emailService;

    @Autowired
    public SpringUserRepository springUserRepository;

    @Autowired
    public SpringUserRepoImp springUserRepoImp;

    @Autowired
    public PasswordResetTokenRepoImp passwordResetTokenRepoImp;

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam(value = "reset_password_token", required = false) String token,  Model model, HttpServletRequest request)
    {
        // Check if the given token is valid
        boolean isPasswordResetTokenValid = passwordResetTokenRepoImp.validatePasswordResetToken(token);

        // Grab Spring User associated to the given password reset token
        try
        {
            SpringUser springUser = passwordResetTokenRepoImp.getSpringUserByToken(token);
            model.addAttribute("springUserEmail", springUser.getSpringUserEmail());
        }

        catch (InvalidResetPasswordToken e)
        {
            model.addAttribute("springUserEmail", null);
        }

        // Feed the email of the spring User to the model
        model.addAttribute("isUserLoggedIn", false);
        model.addAttribute("isTokenValid", isPasswordResetTokenValid);
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "Reset Password");
        model.addAttribute("pageDescription", "Hours Manager Web Application Reset Password Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, reset password");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/reset-password");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        // Validate the token
        return "reset-password";
    }


    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> changeSpringUserPassword(@RequestBody ResetPasswordInfo resetPasswordInfo)
    {
        // Change the password
        try
        {
            // Try to grab the user by the given email
            SpringUser springUser = springUserRepository.findSpringUserBySpringUserEmail(resetPasswordInfo.getEmail());

            // Try to change his/her password
            springUserRepoImp.updatePassword(springUser, resetPasswordInfo.getPassword());

            // Send email to the user with the message that the password has been successfully updated
            try
            {
                emailService.sendGenericResetPasswordEmailToUser(resetPasswordInfo.getEmail());
            }

            catch (IOException | EmailServiceException e)
            {
                e.printStackTrace();
            }

            // Delete all reset password tokens
            passwordResetTokenRepoImp.deleteAllPasswordResetTokensBySpringUser(springUser);

            // Send a response that the password has been successfully updated
            return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadSuccessResponse("Your password has been successfully changed").getPayload());

        }

        catch (ValidationError e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
        }

        catch (DbException  e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
        }
    }
}
