package com.hoursmanager.HoursManager.exceptions;

public class UserNotFound extends RuntimeException
{
    public UserNotFound(String message)
    {
        super(message);
    }

}
