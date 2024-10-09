package com.hoursmanager.HoursManager.utils;

public class StringFormattingUtils
{
    public static String getPossessiveNounFromName(String name)
    {
        /*
        * Get the possessive noun from a given name
        * If it does not end in s it should be name + 's
        * If it ends in s then it should be name + 's
        * */
        if (name.endsWith("s"))
        {
            return name + "'";
        }

        else
        {
            return name + "'s";
        }
    }
}
