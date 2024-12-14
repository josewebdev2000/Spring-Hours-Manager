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

    public static String formatRateAmountByType(String rateAmount, String rateType)
    {
        /*
        * Prepare a string to show the Rate amount with their Type
        *
        * */

        // Final rep to return
        String rateRep = "";

        switch (rateType.toLowerCase())
        {
            case "hourly":
            {
               rateRep = "$" + rateAmount + "/hr";
               break;
            }

            case "daily":
            {
                rateRep = "$" + rateAmount + "/day";
                break;
            }

            case "weekly":
            {
                rateRep = "$" + rateAmount + "/week";
                break;
            }

            case "biweekly":
            {
                rateRep = "$" + rateAmount + "/two weeks";
                break;
            }

            case "monthly":
            {
                rateRep = "$" + rateAmount + "/month";
                break;
            }
        }

        return rateRep;
    }
}
