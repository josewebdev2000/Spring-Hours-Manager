package com.hoursmanager.HoursManager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Represents JSON payload to send to Email Sending Microservice */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto
{
    @JsonProperty("apiKey")
    private String apiKey;

    @JsonProperty("toEmail")
    private String toEmail;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("htmlContent")
    private String htmlContent;
}
