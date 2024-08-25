package com.hoursmanager.HoursManager.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator extends BasicStrValidator
{
    private static final String NAME_REGEX = "^[A-Za-z\\s.,:;]+$";

    public static boolean isValidName(String name)
    {
        // Check if regex matches
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }
}
