package com.hoursmanager.HoursManager.utils;

import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;

/*
*
* Common Operations run on Models multiple times throughout the app
* */
public class DbUtils
{
    public static boolean isUserIdInDb(Long userId, SpringUserRepository springUserRepository)
    {
        // Check whether a user exists in the DB or not through his/her ID
        SpringUser springUser = springUserRepository.findById(userId).orElse(null);

        return springUser != null;
    }

    public static String getUserProfilePic(Long userId, SpringUserRepository springUserRepository, String defaultImgPath)
    {
        // Grab the user first
        SpringUser springUser = springUserRepository.findById(userId).orElse(null);

        if (springUser != null)
        {
            String springUserPicUrl = springUser.getSpringUserPicUrl();

            if (springUserPicUrl != null)
            {
                return springUserPicUrl;
            }

            else
            {
                return defaultImgPath;
            }
        }

        else
        {
            return defaultImgPath;
        }

    }
}
