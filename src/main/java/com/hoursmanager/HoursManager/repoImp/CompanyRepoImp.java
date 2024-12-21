package com.hoursmanager.HoursManager.repoImp;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.AddJobPayload;
import com.hoursmanager.HoursManager.forms.CompanyPayload;
import com.hoursmanager.HoursManager.models.Company;
import com.hoursmanager.HoursManager.models.Job;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.CompanyRepository;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.validators.EmailValidator;
import com.hoursmanager.HoursManager.validators.NameValidator;
import com.hoursmanager.HoursManager.validators.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyRepoImp
{
    private final CompanyRepository companyRepository;

    public Company addNewCompany(SpringUser springUserOwner, AddJobPayload addJobPayload) throws ValidationError
    {
        // Grab data to insert a new company
        Company newCompany = new Company();
        newCompany.setCompanyUser(springUserOwner);

        // Set other company attributes
        if (!NameValidator.isValidName(addJobPayload.getCompanyPayload().getCompanyName()))
        {
            throw new ValidationError("Invalid Company Name");
        }

        /*
        if (!EmailValidator.isValidEmail(addJobPayload.getCompanyPayload().getCompanyContactEmail()))
        {
            throw new ValidationError("Invalid Company Email");
        }
        */

        /* Needs Unit Testing
        if (!PhoneNumberValidator.isValidPhoneNumber(addJobPayload.getCompanyPayload().getCompanyContactPhoneNumber()))
        {
            throw new ValidationError("Invalid Company Phone Number");
        }
         */

        newCompany.setCompanyName(addJobPayload.getCompanyPayload().getCompanyName());
        newCompany.setCompanyContactEmail(addJobPayload.getCompanyPayload().getCompanyContactEmail());
        newCompany.setCompanyContactPhoneNumber(addJobPayload.getCompanyPayload().getCompanyContactPhoneNumber());

        // Insert company in DB
        return companyRepository.save(newCompany);
    }

    public Company updateCompany(Job job, CompanyPayload companyPayload) throws ValidationError
    {
        // Get the company for this job
        Company company = job.getJobCompany();

        if (!NameValidator.isValidName(companyPayload.getCompanyName()))
        {
            throw new ValidationError("Invalid Company Name");
        }

        // Change values
        company.setCompanyName(companyPayload.getCompanyName());
        company.setCompanyContactEmail(companyPayload.getCompanyContactEmail());
        company.setCompanyContactPhoneNumber(companyPayload.getCompanyContactPhoneNumber());

        // Return saved result
        return companyRepository.save(company);
    }
}
