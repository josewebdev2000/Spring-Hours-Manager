package com.hoursmanager.HoursManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SessionAttribute
{
    USER_ID("userId");

    private final String key;
}
