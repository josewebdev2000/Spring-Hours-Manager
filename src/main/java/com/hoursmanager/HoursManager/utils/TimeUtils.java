package com.hoursmanager.HoursManager.utils;

import java.time.Year;

public class TimeUtils
{
    public static int getCurrentYear()
    {
        return Year.now().getValue();
    }
}
