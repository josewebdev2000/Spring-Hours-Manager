package com.hoursmanager.HoursManager.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* Data class meant to represent the JSON payload sent from the front-end to the POST /contact route
* It provides the required info to send the client an email
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo
{
    @JsonProperty("name")
    public String name;

    @JsonProperty("email")
    public String email;

    @JsonProperty("subject")
    public String subject;

    @JsonProperty("request")
    public String message;
}
