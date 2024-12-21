package com.hoursmanager.HoursManager.repoImp;

import com.hoursmanager.HoursManager.enums.PayRateEnum;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.AddJobPayload;
import com.hoursmanager.HoursManager.forms.PayRatePayload;
import com.hoursmanager.HoursManager.models.Job;
import com.hoursmanager.HoursManager.models.PayRate;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.PayRateRepository;
import com.hoursmanager.HoursManager.validators.NameValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class PayRateRepoImp
{
    private final PayRateRepository payRateRepository;

    public PayRate addNewPayRate(SpringUser springUserOwner, Job jobOwning, AddJobPayload addJobPayload) throws ValidationError
    {
        // Create new Pay Rate
        PayRate payRate = new PayRate();

        // Set Foreign Keys
        payRate.setPayRateUser(springUserOwner);
        payRate.setPayRateJob(jobOwning);

        // Validate fields
        if (!NameValidator.isValidName(addJobPayload.getPayRatePayload().getPayRateType()))
        {
            throw new ValidationError("Invalid Rate Type");
        }

        if (addJobPayload.getPayRatePayload().getPayRateAmount() <= 1)
        {
            throw new ValidationError("Rate Amount must be bigger than 0");
        }

        payRate.setPayRateType(PayRateEnum.valueOf(addJobPayload.getPayRatePayload().getPayRateType()));
        payRate.setPayRateAmount(BigDecimal.valueOf(addJobPayload.getPayRatePayload().getPayRateAmount()));

        return payRateRepository.save(payRate);
    }

    public PayRate updatePayRate(Job job, PayRatePayload payRatePayload)
    {
        // Get the PayRate for this Job
        PayRate payRate = job.getPayRates().get(0);

        // Validate fields
        if (!NameValidator.isValidName(payRatePayload.getPayRateType()))
        {
            throw new ValidationError("Invalid Rate Type");
        }

        if (payRatePayload.getPayRateAmount() <= 1)
        {
            throw new ValidationError("Rate Amount must be bigger than 0");
        }

        // Update Pay Rate fields
        payRate.setPayRateType(PayRateEnum.valueOf(payRatePayload.getPayRateType()));
        payRate.setPayRateAmount(BigDecimal.valueOf(payRatePayload.getPayRateAmount()));

        return payRateRepository.save(payRate);
    }
}
