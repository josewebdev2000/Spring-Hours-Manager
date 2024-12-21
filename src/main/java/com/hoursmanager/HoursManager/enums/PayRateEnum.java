package com.hoursmanager.HoursManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayRateEnum
{
    hourly("hourly"),
    daily("daily"),
    weekly("weekly"),
    biweekly("biweekly"),
    monthly("monthly");

    private final String payRateType;

    public static PayRateEnum fromValue(String val)
    {
        for (PayRateEnum payRate : PayRateEnum.values())
        {
            if (payRate.payRateType.equals(val))
            {
                return payRate;
            }
        }

        throw new IllegalArgumentException("Unknown enum value: " + val);
    }
}
