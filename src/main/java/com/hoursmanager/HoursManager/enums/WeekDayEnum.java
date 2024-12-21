package com.hoursmanager.HoursManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeekDayEnum
{
    monday("monday"),
    tuesday("tuesday"),
    wednesday("wednesday"),
    thursday("thursday"),
    friday("friday"),
    saturday("saturday"),
    sunday("sunday");

    private final String weekDay;

    public static WeekDayEnum fromValue(String val)
    {
        for (WeekDayEnum weekDay : WeekDayEnum.values())
        {
            if (weekDay.weekDay.equals(val))
            {
                return weekDay;
            }
        }

        throw new IllegalArgumentException("Unknown enum value: " + val);
    }
}
