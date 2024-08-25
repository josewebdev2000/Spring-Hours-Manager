package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.exceptions.EmailServiceException;
import com.hoursmanager.HoursManager.forms.ContactInfo;
import com.hoursmanager.HoursManager.services.EmailService;
import com.hoursmanager.HoursManager.utils.TimeUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import com.hoursmanager.HoursManager.views.JsonPayloadErrorResponse;
import com.hoursmanager.HoursManager.views.JsonPayloadSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class ContactController
{
    private final EmailService emailService;

    public ContactController(EmailService emailService)
    {
        this.emailService = emailService;
    }

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

    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> sendContactEmail(@RequestBody ContactInfo contactInfo)
    {
        try
        {
            // Use the email service to try to send an email
            String responseMessage = emailService.sendContactEmailToUser(contactInfo);

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
