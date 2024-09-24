package com.hoursmanager.HoursManager.exceptions;

public class ValidationError extends RuntimeException
{
    public ValidationError(String message)
    {
        super(message);
    }
}
