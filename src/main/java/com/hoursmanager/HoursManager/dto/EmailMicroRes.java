package com.hoursmanager.HoursManager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* Represent the payload received from the back-end when a request is sent
* */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMicroRes
{
    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;
}
