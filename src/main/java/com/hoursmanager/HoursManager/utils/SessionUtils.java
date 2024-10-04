package com.hoursmanager.HoursManager.utils;

/*
* Useful file for dealing with session operations
* */

import jakarta.servlet.http.HttpSession;

public class SessionUtils
{
    public static boolean isSessionValueValid(HttpSession session, String sessionKey)
    {
        // Return true if a given session value is found
        return session != null && session.getAttribute(sessionKey) != null;
    }
}
