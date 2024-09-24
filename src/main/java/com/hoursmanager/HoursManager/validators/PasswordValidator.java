package com.hoursmanager.HoursManager.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator extends BasicStrValidator
{
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,}$";

    public static boolean isValidPassword(String password)
    {
        // Check if regex matches
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
