package com.hoursmanager.HoursManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedirectUrl
{
    HOME("/"),
    DASHBOARD("/dashboard");

    private final String redirectUrl;
}
