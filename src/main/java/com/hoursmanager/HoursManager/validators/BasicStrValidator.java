package com.hoursmanager.HoursManager.validators;

/*
* Basic Validator to check
* A string is not null
* A string is not empty
* */

public class BasicStrValidator
{
    public static boolean isValidBasicStr(String str)
    {
        return str != null && !str.isEmpty() && !str.isBlank();
    }
}
