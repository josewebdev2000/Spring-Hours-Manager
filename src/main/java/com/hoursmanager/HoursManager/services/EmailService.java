package com.hoursmanager.HoursManager.services;

import com.hoursmanager.HoursManager.dto.EmailDto;
import com.hoursmanager.HoursManager.dto.EmailMicroRes;
import com.hoursmanager.HoursManager.exceptions.EmailServiceException;
import com.hoursmanager.HoursManager.forms.ContactInfo;
import com.hoursmanager.HoursManager.utils.TimeUtils;

import com.hoursmanager.HoursManager.validators.BasicStrValidator;
import com.hoursmanager.HoursManager.validators.EmailValidator;
import com.hoursmanager.HoursManager.validators.NameValidator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class EmailService
{
    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;

    @Value("${email.service.api.endpoint}")
    private String emailServiceEndpoint;

    @Value("${email.service.api.key}")
    private String emailServiceApiKey;

    @Value("${email.service.html.template.path}")
    private String emailHtmlTemplatePath;

    @Value("${email.service.html.template.admin.path}")
    private String adminEmailHtmlTemplatePath;

    public EmailService(RestTemplate restTemplate, ResourceLoader resourceLoader)
    {
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
    }

    private void validateContactInfo(ContactInfo contactInfo) throws EmailServiceException
    {
        // Validate email
        if (!(EmailValidator.isValidBasicStr(contactInfo.getEmail()) && EmailValidator.isValidEmail(contactInfo.getEmail())))
        {
            System.out.println(EmailValidator.isValidEmail(contactInfo.getEmail()));
            throw new EmailServiceException("Email address is required and must be valid");
        }

        // Validate name
        if (!(NameValidator.isValidBasicStr(contactInfo.getName()) && NameValidator.isValidName(contactInfo.getName())))
        {
            throw new EmailServiceException("Name is required and must be valid");
        }

        // Validate the subject
        if (!BasicStrValidator.isValidBasicStr(contactInfo.getSubject()))
        {
            throw new EmailServiceException("Subject is required");
        }

        // Validate the user's message
        if (!BasicStrValidator.isValidBasicStr(contactInfo.getRequest()))
        {
            throw new EmailServiceException("Message is required");
        }
    }

    private String prepareContactEmailForUser(String name, String date) throws IOException
    {
        // Grab the HTML from the no-reply.html template
        Resource resource = resourceLoader.getResource(emailHtmlTemplatePath);
        String emailTemplate = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);

        // Replace placeholders with actual data
        emailTemplate = emailTemplate
                .replace("{{NAME}}", name)
                .replace("{{DATE}}", date);

        return emailTemplate;
    }

    private String prepareAdminEmailForAdmin(String name, String request, String date) throws IOException
    {
        // Grab the HTML from the admin-template.html template
        Resource resource = resourceLoader.getResource(adminEmailHtmlTemplatePath);
        String adminTemplate = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);

        // Replace placeholders with actual data
        adminTemplate = adminTemplate
                .replace("{{NAME}}", name)
                .replace("{{MESSAGE}}", request)
                .replace("{{DATE}}", date);

        return adminTemplate;
    }

    private String sendEmailFromJsonRequest(EmailDto emailDto) throws IOException, EmailServiceException
    {
        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Prepare the request to send
        HttpEntity<EmailDto> requestEntity = new HttpEntity<>(emailDto, headers);

        // Make the API request to send the email
        try
        {
            ResponseEntity<EmailMicroRes> response = restTemplate.exchange(
                    emailServiceEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    EmailMicroRes.class
            );

            // Read the response body
            EmailMicroRes emailMicroRes = response.getBody();

            // In case of error, throw exception
            if (emailMicroRes == null ||!emailMicroRes.isSuccess())
            {
                throw new EmailServiceException(emailMicroRes != null ? emailMicroRes.getMessage() : "No response from email service");
            }

            // By this point, return success message
            return emailMicroRes.getMessage();
        }

        catch (Exception e)
        {
            throw new EmailServiceException("Error while sending email");
        }
    }

    public String sendAdminEmailToAdmin(ContactInfo contactInfo) throws IOException, EmailServiceException
    {
        // Validate contact info
        validateContactInfo(contactInfo);

        // Grab email content
        String htmlAdminEmailContent = prepareAdminEmailForAdmin(
                "Hours Manager Administrator",
                contactInfo.getRequest(),
                TimeUtils.getCurrentDate()
        );

        // Prepare the JSON payload to send to the microservice
        EmailDto emailDto = new EmailDto(
                emailServiceApiKey,
                "webad78573@gmail.com",
                contactInfo.getSubject(),
                htmlAdminEmailContent
        );

        // Send the admin email
        return sendEmailFromJsonRequest(emailDto);
    }

    public String sendContactEmailToUser(ContactInfo contactInfo) throws IOException, EmailServiceException
    {
        // Validate contact info
        validateContactInfo(contactInfo);

        // Grab email content
        String htmlEmailContent = prepareContactEmailForUser(
                contactInfo.getName(),
                TimeUtils.getCurrentDate()
        );

        // Prepare the JSON payload to send to the microservice
        EmailDto emailDto = new EmailDto(
                emailServiceApiKey,
                contactInfo.getEmail(),
                "Hours Manager Received Your Request",
                htmlEmailContent
        );

        // Send the email already
        return sendEmailFromJsonRequest(emailDto);
    }
}
