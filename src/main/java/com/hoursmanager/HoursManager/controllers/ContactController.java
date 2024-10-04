package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.exceptions.EmailServiceException;
import com.hoursmanager.HoursManager.forms.ContactInfo;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.services.EmailService;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.SessionUtils;
import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import com.hoursmanager.HoursManager.views.JsonPayloadErrorResponse;
import com.hoursmanager.HoursManager.views.JsonPayloadSuccessResponse;
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

import java.util.Map;

@Controller
@AllArgsConstructor
public class ContactController
{
    private final EmailService emailService;
    private final SpringUserRepository springUserRepository;

    @GetMapping("/contact")
    public String contact(Model model, HttpServletRequest request, HttpSession session)
    {
        // Determine if the user is logged in
        boolean isUserLoggedIn = SessionUtils.isSessionValueValid(session, "userId");

        // If the user is logged in
        if (isUserLoggedIn)
        {
            // Grab the user's ID
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());

            // Grab the actual user
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the username
            String springUserName = springUser.getSpringUserName();

            // Grab the profile picture
            String springUserProfilePic = DbUtils.getUserProfilePic(userId, springUserRepository, "/img/avatars/user.png");

            // Add that to the model
            model.addAttribute("springUserName", springUserName);
            model.addAttribute("springUserPicUrl", springUserProfilePic);
        }

        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
        model.addAttribute("pageTitle", "Contact");
        model.addAttribute("pageDescription", "Hours Manager Web Application Contact Page");
        model.addAttribute("pageKeywords", "working, hours, tracker, budget, calculations, manager, contact page, contact us");
        model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/contact");
        model.addAttribute("year", TimeUtils.getCurrentYear());
        model.addAttribute("request", request);

        return "contact";
    }

    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> sendContactEmail(@RequestBody ContactInfo contactInfo)
    {
        try
        {
            // Use the email service to try to send an email
            String responseMessage = emailService.sendContactEmailToUser(contactInfo);

            // Use the email service to send email the website administrator
            emailService.sendAdminEmailToAdmin(contactInfo);

            // In case of success, return Ok response
            return ResponseEntity.ok(new JsonPayloadSuccessResponse(responseMessage).getPayload());
        }

        catch (EmailServiceException e)
        {
            return ResponseEntity.badRequest().body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse("A system error cancelled email sending operations").getPayload());
        }
    }

}
