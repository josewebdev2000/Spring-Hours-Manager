package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.enums.RedirectUrl;
import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.exceptions.DbException;
import com.hoursmanager.HoursManager.exceptions.EmailServiceException;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.PasswordUpdateInfo;
import com.hoursmanager.HoursManager.forms.ProfilePicUrlInfo;
import com.hoursmanager.HoursManager.forms.UsernameUpdateInfo;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repoImp.SpringUserRepoImp;
import com.hoursmanager.HoursManager.repositories.JobRepository;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.services.EmailService;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.StringFormattingUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import com.hoursmanager.HoursManager.views.JsonPayloadErrorResponse;
import com.hoursmanager.HoursManager.views.JsonPayloadRedirectUrl;
import com.hoursmanager.HoursManager.views.JsonPayloadSuccessResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/dashboard")
@Controller
@AllArgsConstructor
public class ProfileController
{
    @Autowired
    private final EmailService emailService;

    @Autowired
    private final SpringUserRepository springUserRepository;

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final SpringUserRepoImp springUserRepoImp;

    // GET Route for Profile Page (profile.html)
    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request, HttpSession session)
    {
        // Check the user is logged in
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) == null)
        {
            return String.format("redirect:%s", RedirectUrl.HOME.getRedirectUrl());
        }

        else if (!DbUtils.isUserIdInDb((Long) session.getAttribute(SessionAttribute.USER_ID.getKey()), springUserRepository))
        {
            return String.format("redirect:%s", RedirectUrl.HOME.getRedirectUrl());
        }

        else
        {
            // Grab the user's ID from the current session
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());

            // Grab the current user
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the username
            String springUserName = springUser.getSpringUserName();

            // Grab the email address
            String springUserEmail = springUser.getSpringUserEmail();

            // Grab the user's profile picture
            String springUserPicUrl = DbUtils.getUserProfilePic(userId, springUserRepository, "/img/avatars/user.png");

            // Grab the number of jobs per user
            Long numOfSpringUserJobs = jobRepository.countBySpringUser(springUser);

            // Add Dynamic data to the template
            model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
            model.addAttribute("pageTitle", "User Profile");
            model.addAttribute("pageDescription", "Hours Manager Web Application User Profile");
            model.addAttribute("pageKeywords", "working, hours, tracker, user, profile, settings, password change, forgot password");
            model.addAttribute("springUserName", springUserName);
            model.addAttribute("springUserPossessiveName", StringFormattingUtils.getPossessiveNounFromName(springUserName));
            model.addAttribute("springUserEmail", springUserEmail);
            model.addAttribute("springUserPicUrl", springUserPicUrl);
            model.addAttribute("numOfSpringUserJobs", numOfSpringUserJobs);
            model.addAttribute("pageUrl", UrlUtils.getBaseUrl() + "/dashboard/profile");
            model.addAttribute("request", request);

            return "profile";
        }
    }

    // Make POST Routes to modify username, password, and avatar pic
    @PostMapping("/profile/passwordUpdate")
    public ResponseEntity<Map<String, String>> updateSpringUserPassword(@RequestBody PasswordUpdateInfo passwordUpdateInfo, HttpSession session)
    {
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse("You must be logged in to change the password").getPayload());
        }

        else
        {
            // Grab the user from the session id
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the password from the front-end
            String newPassword = passwordUpdateInfo.getPassword();

            // Try to update the password
            try
            {
                springUserRepoImp.updatePassword(springUser, newPassword);
                // Send the email about changed password
                try
                {
                    emailService.sendUserSpecificResetPasswordEmailToUser(springUser.getSpringUserEmail(), springUser.getSpringUserName());
                }

                catch (IOException | EmailServiceException e)
                {
                    e.printStackTrace();
                }

                return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadSuccessResponse("Your password was successfully changed").getPayload());
            }

            catch (ValidationError e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
            }

            catch (DbException e)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
            }
        }
    }

    @PostMapping("/profile/usernameUpdate")
    public ResponseEntity<Map<String, String>> updateSpringUserUsername(@RequestBody UsernameUpdateInfo usernameUpdateInfo, HttpSession session)
    {
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse("You must be logged in to change the profile username").getPayload());
        }

        else
        {
            // Grab the user from the session id
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the username from the front-end
            String newUsername = usernameUpdateInfo.getUsername();

            // Try to update the username
            try
            {
                springUserRepoImp.updateSpringUserName(springUser, newUsername);
                return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadRedirectUrl(UrlUtils.getBaseUrl() + "/dashboard/profile").getPayload());
            }

            catch (ValidationError e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
            }

            catch (DbException e)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
            }
        }
    }

    @PostMapping("/profile/avatarUpdate")
    public ResponseEntity<Map<String, String>> updateSpringUserProfilePicture(@RequestBody ProfilePicUrlInfo profilePicUrlInfo, HttpSession session)
    {
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse("You must be logged in to change the profile picture").getPayload());
        }

        else
        {
            // Grab the user from the session id
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Grab the URL of the new picture
            String newPicUrl = profilePicUrlInfo.getPicUrl();

            // Update the userPicUrl
            try
            {
                springUserRepoImp.updateProfilePicture(springUser, newPicUrl);
                return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadRedirectUrl(UrlUtils.getBaseUrl() + "/dashboard/profile").getPayload());
            }

            catch (ValidationError e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
            }

            catch (DbException e)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
            }
        }
    }

    @PostMapping("/profile/deleteSpringUser")
    public ResponseEntity<Map<String, String>> deleteSpringUserProfile(HttpSession session, HttpServletResponse response, UriComponentsBuilder uriBuilder)
    {
        // Verify we can extract the SpringUser ID from the session
        if (session.getAttribute(SessionAttribute.USER_ID.getKey()) == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonPayloadErrorResponse("You must be logged in to delete your account").getPayload());
        }

        try
        {

            // Grab the userId
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID.getKey());
            SpringUser springUser = springUserRepository.findById(userId).orElse(null);

            // Delete the user
            springUserRepoImp.deleteSpringUser(springUser);

            // Invalidate the session
            session.invalidate();

            // Remove JSESSION Cookie from the browser
            Cookie jSessionCookie = new Cookie("JSESSIONID", null);
            jSessionCookie.setPath("/");
            jSessionCookie.setHttpOnly(true);
            jSessionCookie.setMaxAge(0);
            response.addCookie(jSessionCookie);

            // Send payload to redirect Home
            String homeUri = uriBuilder.path(RedirectUrl.HOME.getRedirectUrl()).build().toUriString();
            return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadRedirectUrl(homeUri).getPayload());
        }

        catch (DbException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonPayloadErrorResponse(e.getMessage()).getPayload());
        }

    }
}
