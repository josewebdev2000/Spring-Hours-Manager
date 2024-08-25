package com.hoursmanager.HoursManager.utils;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class TimeUtils
{
    public static int getCurrentYear()
    {
        return Year.now().getValue();
    }
    public static String getCurrentDate()
    {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Have the date on month day, year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        return currentDate.format(formatter);
    }
}
