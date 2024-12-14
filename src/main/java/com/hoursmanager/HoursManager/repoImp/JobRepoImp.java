package com.hoursmanager.HoursManager.repoImp;

import com.hoursmanager.HoursManager.models.Job;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.JobRepository;
import com.hoursmanager.HoursManager.utils.StringFormattingUtils;
import com.hoursmanager.HoursManager.views.JobView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class JobRepoImp
{
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
                    StringFormattingUtils.formatRateAmountByType(job.getPayRates().get(0).getPayRateAmount().toString(), job.getPayRates().get(0).getPayRateType().toString()),
                    job.getPayChecks().get(0).getPayCheckPaymentDay().toString()
            );

            // Add it to the jobViews ArrayList
            jobViews.add(jobView);
        }

        return jobViews;
    }
}
