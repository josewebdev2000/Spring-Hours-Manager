package com.hoursmanager.HoursManager.exceptions;

public class InvalidResetPasswordToken extends IllegalArgumentException
{
    public InvalidResetPasswordToken(String message)
    {
        super(message);
    }
}
