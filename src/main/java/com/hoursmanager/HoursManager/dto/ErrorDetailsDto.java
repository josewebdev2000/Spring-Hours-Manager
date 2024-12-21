package com.hoursmanager.HoursManager.dto;

/* Describe any error details */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailsDto
{
    private LocalDateTime timeStamp;
    private String error;
    private String details;
}
