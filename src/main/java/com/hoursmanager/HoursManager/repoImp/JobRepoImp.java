package com.hoursmanager.HoursManager.repoImp;

import com.hoursmanager.HoursManager.enums.PayRateEnum;
import com.hoursmanager.HoursManager.enums.WeekDayEnum;
import com.hoursmanager.HoursManager.exceptions.DbException;
import com.hoursmanager.HoursManager.exceptions.JobNotFound;
import com.hoursmanager.HoursManager.exceptions.UserNotFound;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.AddJobPayload;
import com.hoursmanager.HoursManager.models.*;
import com.hoursmanager.HoursManager.repositories.*;
import com.hoursmanager.HoursManager.utils.DbUtils;
import com.hoursmanager.HoursManager.utils.StringFormattingUtils;
import com.hoursmanager.HoursManager.validators.BasicStrValidator;
import com.hoursmanager.HoursManager.views.JobView;
import com.hoursmanager.HoursManager.views.details.CompanyDetailsView;
import com.hoursmanager.HoursManager.views.details.JobDetailsView;
import com.hoursmanager.HoursManager.views.details.PayCheckDetailsView;
import com.hoursmanager.HoursManager.views.details.PayRateDetailsView;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class JobRepoImp
{
    private final SpringUserRepository springUserRepository;
    private final JobRepository jobRepository;

    public List<JobView> getJobsBySpringUser(SpringUser springUser)
    {
        // Get data from the repository
        List<Job> jobsFromDb = jobRepository.getJobsBySpringUser(springUser);

        // Generate a list of JobViews
        List<JobView> jobViews = new ArrayList<JobView>();

        // Parse each Job to a JobView
        for (Job job : jobsFromDb)
        {
            // Generate current job View
            JobView jobView = new JobView(
                    job.getJobId(),
                    job.getJobName(),
                    job.getJobDescription(),
                    job.getJobCompany().getCompanyName(),
                    StringFormattingUtils.formatRateAmountByType(String.valueOf(job.getPayRates().get(0).getPayRateAmount().toString()), String.valueOf(PayRateEnum.valueOf(job.getPayRates().get(0).getPayRateType().toString()))),
                    StringFormattingUtils.capitalizeFirstLetter(job.getPayChecks().get(0).getPayCheckPaymentDay().toString())
            );

            // Add it to the jobViews ArrayList
            jobViews.add(jobView);
        }

        return jobViews;
    }

    public JobDetailsView getJobDetailsView(SpringUser springUser, Long jobId) throws JobNotFound
    {
        // Get Job Data from the SpringUser
        List<Job> jobsFromDb = springUser.getJobs().stream().filter(job -> Objects.equals(job.getJobId(), jobId)).toList();

        if (jobsFromDb.isEmpty())
        {
            throw new JobNotFound("");
        }

        Job jobFromDb = jobsFromDb.get(0);

        // Grab details from the job
        String jobName = jobFromDb.getJobName();
        String jobDescription = jobFromDb.getJobDescription();
        Company jobCompany = jobFromDb.getJobCompany();
        PayRate jobPayRate = jobFromDb.getPayRates().get(0);
        PayCheck jobPayCheck = jobFromDb.getPayChecks().get(0);

        // Build Pay Check Details View
        PayCheckDetailsView jobPayCheckDetailsView = new PayCheckDetailsView(
                StringFormattingUtils.capitalizeFirstLetter(String.valueOf(WeekDayEnum.fromValue(String.valueOf(jobPayCheck.getPayCheckPaymentDay())))),
                jobPayCheck.getPayCheckTotalPayment(),
                jobPayCheck.getPayCheckTips()
        );

        // Build Pay Rate Details View
        PayRateDetailsView jobPayRateDetailsView = new PayRateDetailsView(
                StringFormattingUtils.capitalizeFirstLetter(String.valueOf(PayRateEnum.fromValue(String.valueOf(jobPayRate.getPayRateType())))),
                jobPayRate.getPayRateAmount()
        );

        // Build Company View
        CompanyDetailsView jobCompanyDetailsView = new CompanyDetailsView(
                jobCompany.getCompanyName(),
                jobCompany.getCompanyContactEmail(),
                jobCompany.getCompanyContactPhoneNumber()
        );

        return new JobDetailsView(
                jobName,
                jobDescription,
                jobCompanyDetailsView,
                jobPayRateDetailsView,
                jobPayCheckDetailsView
        );
    }

    @Transactional
    public String addNewJob(AddJobPayload addJobPayload, CompanyRepoImp companyRepoImp, PayRateRepoImp payRateRepoImp, PayCheckRepoImp payCheckRepoImp) throws ValidationError, DbException, UserNotFound
    {
        try
        {
            // Grab User From JobPayload
            SpringUser springUserOwner = DbUtils.getSpringUserFromAddJobPayload(addJobPayload, springUserRepository);

            // Grab new company
            Company newCompany = companyRepoImp.addNewCompany(springUserOwner, addJobPayload);

            // Insert Job Now
            Job newJob = new Job();
            newJob.setJobUser(springUserOwner);
            newJob.setJobCompany(newCompany);

            // Validate Job Name and Description
            if (!BasicStrValidator.isValidBasicStr(addJobPayload.getJobPayload().getJobName()))
            {
                throw new ValidationError("Invalid Job Name");
            }

            if (!BasicStrValidator.isValidBasicStr(addJobPayload.getJobPayload().getJobDescription()))
            {
                throw new ValidationError("Invalid Job Description");
            }

            newJob.setJobName(addJobPayload.getJobPayload().getJobName());
            newJob.setJobDescription(addJobPayload.getJobPayload().getJobDescription());

            // Grab the Saved Job
            Job savedJob = jobRepository.save(newJob);

            // Insert New Pay Rate
            PayRate newPayRate = payRateRepoImp.addNewPayRate(springUserOwner, savedJob, addJobPayload);

            // Insert New Pay Check
            PayCheck newPayCheck = payCheckRepoImp.addNewPayCheck(springUserOwner, savedJob, addJobPayload);

            // Add Pay Rate and Pay Rate to Job
            savedJob.getPayRates().add(newPayRate);

            // Add Pay Check and Pay Check to Job
            savedJob.getPayChecks().add(newPayCheck);
            jobRepository.save(savedJob);

            return "New (" +  newJob.getJobName() + " - " + newCompany.getCompanyName() + " ) Job could be added successfully";
        }
        catch (DbException e)
        {
            e.printStackTrace();
            throw new DbException("Failed to add new job");
        }
    }

    @Transactional
    public JobDetailsView editJob(Long jobId, AddJobPayload addJobPayload, CompanyRepoImp companyRepoImp, PayRateRepoImp payRateRepoImp, PayCheckRepoImp payCheckRepoImp) throws ValidationError, DbException, UserNotFound, JobNotFound
    {
        try
        {
            // Grab SpringUser from payload
            SpringUser springUser = DbUtils.getSpringUserFromAddJobPayload(addJobPayload, springUserRepository);

            // Grab job id only if it belongs to this user
            List<Job> jobs = springUser.getJobs().stream().filter(job -> Objects.equals(job.getJobId(), jobId)).toList();

            if (jobs.isEmpty())
            {
                throw new JobNotFound("");
            }

            // Grab the job to edit
            Job job = jobs.get(0);

            // Update Job
            job.setJobName(addJobPayload.getJobPayload().getJobName());
            job.setJobDescription(addJobPayload.getJobPayload().getJobDescription());

            job = jobRepository.save(job);

            // Grab updated company data
            Company updatedCompany = companyRepoImp.updateCompany(job, addJobPayload.getCompanyPayload());

            // Grab updated Pay Rate Data
            PayRate updatedPayRate = payRateRepoImp.updatePayRate(job, addJobPayload.getPayRatePayload());

            // Grab updated Pay Check Data
            PayCheck updatedPayCheck = payCheckRepoImp.updatePayCheck(job, addJobPayload.getPayCheckPayload());

            // Add Updated Data to JobDetailsView
            // Build Pay Check Details View
            PayCheckDetailsView updatedJobPayCheckDetailsView = new PayCheckDetailsView(
                    StringFormattingUtils.capitalizeFirstLetter(String.valueOf(WeekDayEnum.fromValue(String.valueOf(updatedPayCheck.getPayCheckPaymentDay())))),
                    updatedPayCheck.getPayCheckTotalPayment(),
                    updatedPayCheck.getPayCheckTips()
            );

            // Build Pay Rate Details View
            PayRateDetailsView updatedJobPayRateDetailsView = new PayRateDetailsView(
                    StringFormattingUtils.capitalizeFirstLetter(String.valueOf(PayRateEnum.fromValue(String.valueOf(updatedPayRate.getPayRateType())))),
                    updatedPayRate.getPayRateAmount()
            );

            // Build Company View
            CompanyDetailsView updatedJobCompanyDetailsView = new CompanyDetailsView(
                    updatedCompany.getCompanyName(),
                    updatedCompany.getCompanyContactEmail(),
                    updatedCompany.getCompanyContactPhoneNumber()
            );

            return new JobDetailsView(
                  job.getJobName(),
                  job.getJobDescription(),
                  updatedJobCompanyDetailsView,
                  updatedJobPayRateDetailsView,
                  updatedJobPayCheckDetailsView
            );
        }

        catch (DbException e)
        {
            e.printStackTrace();
            throw new DbException("Failed to edit job");
        }
    }
}
