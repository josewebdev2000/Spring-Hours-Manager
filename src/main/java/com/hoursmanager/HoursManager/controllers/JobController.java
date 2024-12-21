package com.hoursmanager.HoursManager.controllers;

import com.hoursmanager.HoursManager.enums.RedirectUrl;
import com.hoursmanager.HoursManager.enums.SessionAttribute;
import com.hoursmanager.HoursManager.exceptions.JobNotFound;
import com.hoursmanager.HoursManager.forms.AddJobPayload;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repoImp.CompanyRepoImp;
import com.hoursmanager.HoursManager.repoImp.JobRepoImp;
import com.hoursmanager.HoursManager.repoImp.PayCheckRepoImp;
import com.hoursmanager.HoursManager.repoImp.PayRateRepoImp;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.StringFormattingUtils;
import com.hoursmanager.HoursManager.utils.UrlUtils;
import com.hoursmanager.HoursManager.views.JsonPayloadSuccessResponse;
import com.hoursmanager.HoursManager.views.details.JobDetailsView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/dashboard/job")
@Controller
@AllArgsConstructor
public class JobController
{
    private final SpringUserRepository springUserRepository;
    private final CompanyRepoImp companyRepoImp;
    private final JobRepoImp jobRepoImp;
    private final PayCheckRepoImp payCheckRepoImp;
    private final PayRateRepoImp payRateRepoImp;

    @GetMapping
    public String handleJobActions(
            @RequestParam(name = "action") String action,
            @RequestParam(name = "id", required = false) Long id,
            Model model,
            HttpSession session,
            HttpServletRequest request
    )
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
            assert springUser != null;
            String springUserName = springUser.getSpringUserName();

            // Grab the email address
            String springUserEmail = springUser.getSpringUserEmail();

            // Grab the user's profile picture
            String springUserPicUrl = DbUtils.getUserProfilePic(userId, springUserRepository, "/img/avatars/user.png");

            // Add Dynamic Data to the template
            model.addAttribute("baseUrl", UrlUtils.getBaseUrl());
            model.addAttribute("pageTitle", "Jobs Dashboard");
            model.addAttribute("pageDescription", "Hours Manager Web Application Jobs Dashboard");
            model.addAttribute("pageKeywords", "working, hours, tracker, user, jobs, settings, dashboard");
            model.addAttribute("springUserId", userId);
            model.addAttribute("jobId", id);
            model.addAttribute("springUserName", springUserName);
            model.addAttribute("springUserPossessiveName", StringFormattingUtils.getPossessiveNounFromName(springUserName));
            model.addAttribute("springUserEmail", springUserEmail);
            model.addAttribute("springUserPicUrl", springUserPicUrl);
            model.addAttribute("request", request);

            // Add Job
            if ("add".equals(action))
            {
                // Return Add Job View
                return "add-job";
            }

            // View Job
            if ("view".equals(action))
            {
                if (id != null)
                {
                    // Grab data for the view job model
                    try
                    {
                        JobDetailsView jobDetailsView = jobRepoImp.getJobDetailsView(springUser, id);

                        model.addAttribute("jobName", jobDetailsView.getJobName());
                        model.addAttribute("jobDescription", jobDetailsView.getJobDescription());
                        model.addAttribute("companyName", jobDetailsView.getCompanyDetailsView().getCompanyName());
                        model.addAttribute("companyEmail", jobDetailsView.getCompanyDetailsView().getCompanyEmail());
                        model.addAttribute("companyPhoneNumber", jobDetailsView.getCompanyDetailsView().getCompanyPhoneNumber());
                        model.addAttribute("payRateType", jobDetailsView.getPayRateDetailsView().getPayRateType());
                        model.addAttribute("payRateAmount", jobDetailsView.getPayRateDetailsView().getPayRateAmount());
                        model.addAttribute("payCheckPaymentDay", jobDetailsView.getPayCheckDetailsView().getPayCheckPaymentDay());
                        model.addAttribute("payCheckTotalPayment", jobDetailsView.getPayCheckDetailsView().getPayCheckTotalPayment());
                        model.addAttribute("payCheckTips", jobDetailsView.getPayCheckDetailsView().getPayCheckTips());

                        return "view-job";
                    }

                    catch (JobNotFound e)
                    {
                        return "job-id-error";
                    }
                }

                else
                {
                    return "job-id-error";
                }

            }

            // Edit Job
            if ("edit".equals(action))
            {
                if (id != null)
                {
                    try
                    {
                        JobDetailsView jobDetailsView = jobRepoImp.getJobDetailsView(springUser, id);

                        model.addAttribute("jobName", jobDetailsView.getJobName());
                        model.addAttribute("jobDescription", jobDetailsView.getJobDescription());
                        model.addAttribute("companyName", jobDetailsView.getCompanyDetailsView().getCompanyName());
                        model.addAttribute("companyEmail", jobDetailsView.getCompanyDetailsView().getCompanyEmail());
                        model.addAttribute("companyPhoneNumber", jobDetailsView.getCompanyDetailsView().getCompanyPhoneNumber());
                        model.addAttribute("payRateType", jobDetailsView.getPayRateDetailsView().getPayRateType());
                        model.addAttribute("payRateAmount", jobDetailsView.getPayRateDetailsView().getPayRateAmount());
                        model.addAttribute("payCheckPaymentDay", jobDetailsView.getPayCheckDetailsView().getPayCheckPaymentDay());
                        model.addAttribute("payCheckTotalPayment", jobDetailsView.getPayCheckDetailsView().getPayCheckTotalPayment());
                        model.addAttribute("payCheckTips", jobDetailsView.getPayCheckDetailsView().getPayCheckTips());

                        return "edit-job";
                    }

                    catch (JobNotFound e)
                    {
                        return "job-id-error";
                    }
                }

                else
                {
                    return "job-id-error";
                }

            }

            return "job-action-error";
        }
    }

    @PostMapping("/addNewJob")
    public ResponseEntity<Map<String, String>> addNewJob(@RequestBody AddJobPayload addJobPayload)
    {
        // Add New Job
        String addedJobMessage = jobRepoImp.addNewJob(
                addJobPayload,
                companyRepoImp,
                payRateRepoImp,
                payCheckRepoImp
        );

        return ResponseEntity.status(HttpStatus.OK).body(new JsonPayloadSuccessResponse(addedJobMessage).getPayload());
    }


    @PutMapping("/editJob/{id}")
    public ResponseEntity<JobDetailsView> editJob(
            @PathVariable(name = "id") Long id,
            @RequestBody AddJobPayload addJobPayload
    )
    {
        JobDetailsView updatedJobDetailsView = jobRepoImp.editJob(
                id,
                addJobPayload,
                companyRepoImp,
                payRateRepoImp,
                payCheckRepoImp
        );

        return ResponseEntity.status(HttpStatus.OK).body(updatedJobDetailsView);
    }

}
