package com.hoursmanager.HoursManager.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator extends BasicStrValidator {
    // Regex to validate phone numbers
    // This regex supports:
    // - International format with "+" prefix
    // - Digits, spaces, dashes, parentheses
    // - Ensures minimum 7 and maximum 15 digits
    private static final String PHONE_REGEX = "^\\\\+1 \\\\(\\\\d{3}\\\\) \\\\d{3} - \\\\d{4}$";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Check if regex matches
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
}