package com.hoursmanager.HoursManager.utils;

import com.hoursmanager.HoursManager.exceptions.UserNotFound;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.AddJobPayload;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import org.springframework.web.bind.annotation.RequestBody;

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

    public static SpringUser getSpringUserFromAddJobPayload(AddJobPayload addJobPayload, SpringUserRepository springUserRepository) throws ValidationError
    {
        // Grab User From JobPayload
        if (addJobPayload.getSpringUserId() == null || addJobPayload.getSpringUserId() <= 1)
        {
            throw new ValidationError("Invalid User Id");
        }

        // Set company user
        SpringUser springUserOwner = springUserRepository.findById(addJobPayload.getSpringUserId()).orElseThrow(
                () -> new UserNotFound("User of id " + addJobPayload.getSpringUserId() + " could not be found")
        );

        return springUserOwner;
    }

}
