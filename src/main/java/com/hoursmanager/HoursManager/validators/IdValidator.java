package com.hoursmanager.HoursManager.validators;

public class IdValidator
{
    private static boolean isValidId(Long id)
    {
        return id > 0;
    }
}
