package com.hoursmanager.HoursManager.exceptions;

public class JobNotFound extends RuntimeException
{
    public JobNotFound(String message)
    {
        super(message);
    }
}
