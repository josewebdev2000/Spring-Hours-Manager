package com.hoursmanager.HoursManager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRes
{
    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;
}
