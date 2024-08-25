package com.hoursmanager.HoursManager.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator extends BasicStrValidator
{
    private static final String EMAIL_REGEX = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValidEmail(String email)
    {
        // Check if regex matches
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
