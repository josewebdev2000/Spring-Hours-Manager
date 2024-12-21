package com.hoursmanager.HoursManager.repoImp;

import com.hoursmanager.HoursManager.enums.WeekDayEnum;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.AddJobPayload;
import com.hoursmanager.HoursManager.forms.PayCheckPayload;
import com.hoursmanager.HoursManager.models.Job;
import com.hoursmanager.HoursManager.models.PayCheck;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.PayCheckRepository;
import com.hoursmanager.HoursManager.validators.NameValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class PayCheckRepoImp
{
    private final PayCheckRepository payCheckRepository;
    public PayCheck addNewPayCheck(SpringUser springUser, Job jobOwning, AddJobPayload addJobPayload) throws ValidationError
    {
        // Create a new Pay Check
        PayCheck payCheck = new PayCheck();

        // Set Foreign Keys
        payCheck.setPayCheckUser(springUser);
        payCheck.setPayCheckJob(jobOwning);

        // Validate Fields
        if (!NameValidator.isValidName(addJobPayload.getPayCheckPayload().getPayCheckPaymentDay()))
        {
            throw new ValidationError("Invalid Payment Day");
        }

        if (addJobPayload.getPayCheckPayload().getPayCheckTotalPayment() <= 1)
        {
            throw new ValidationError("Predicted Total Payment Must Be Greater than Zero");
        }

        payCheck.setPayCheckPaymentDay(WeekDayEnum.valueOf(addJobPayload.getPayCheckPayload().getPayCheckPaymentDay()));
        payCheck.setPayCheckTotalPayment(BigDecimal.valueOf(addJobPayload.getPayCheckPayload().getPayCheckTotalPayment()));
        payCheck.setPayCheckTips(BigDecimal.valueOf(addJobPayload.getPayCheckPayload().getPayCheckTips()));

        return payCheckRepository.save(payCheck);
    }

    public PayCheck updatePayCheck(Job job, PayCheckPayload payCheckPayload)
    {
        PayCheck payCheck = job.getPayChecks().get(0);

        // Validate Fields
        if (!NameValidator.isValidName(payCheckPayload.getPayCheckPaymentDay()))
        {
            throw new ValidationError("Invalid Payment Day");
        }

        if (payCheckPayload.getPayCheckTotalPayment() <= 1)
        {
            throw new ValidationError("Predicted Total Payment Must Be Greater than Zero");
        }

        // Change values
        payCheck.setPayCheckPaymentDay(WeekDayEnum.valueOf(payCheckPayload.getPayCheckPaymentDay()));
        payCheck.setPayCheckTotalPayment(BigDecimal.valueOf(payCheckPayload.getPayCheckTotalPayment()));
        payCheck.setPayCheckTips(BigDecimal.valueOf(payCheckPayload.getPayCheckTips()));

        return payCheckRepository.save(payCheck);
    }
}
